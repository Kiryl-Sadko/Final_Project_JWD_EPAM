package by.sadko.training.service;

import by.sadko.training.entity.Contract;
import by.sadko.training.entity.ContractStatus;
import by.sadko.training.entity.Progress;
import by.sadko.training.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Executor of the payed contracts from data base
 * Should be a singleton
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public class ContractExecutor {

    private static final Logger LOGGER = LogManager.getLogger(ContractExecutor.class);

    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final Lock CONTRACT_LOCK = new ReentrantLock();

    private static final AtomicBoolean IS_INSTANCE_EXIST = new AtomicBoolean(false);
    private static final AtomicLong queueTime = new AtomicLong(0);
    private static ContractExecutor instance;

    private int threadPoolCapacity;

    private ContractService contractService;
    private ProductService productService;

    private ThreadPoolExecutor executorService;
    private ScheduledExecutorService scheduledExecutorService;

    private ContractExecutor() {
    }

    /**
     * Returns instance of the executor
     */
    public static ContractExecutor getInstance() {
        if (!IS_INSTANCE_EXIST.get()) {
            INSTANCE_LOCK.lock();

            try {
                if (instance == null) {
                    instance = new ContractExecutor();
                    IS_INSTANCE_EXIST.set(true);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return instance;
    }

    /**
     * Returns execution time of the Executor
     */
    public static AtomicLong getQueueTime() {
        return queueTime;
    }

    /**
     * Returns capacity of the thread pool
     *
     * @return thread pool capacity
     */
    public int getThreadPoolCapacity() {
        return threadPoolCapacity;
    }

    /**
     * Initializing of the Executor
     *
     * @param contractService    - contract service
     * @param productService     - product service
     * @param threadPoolCapacity - capacity fo the Executor's thread pool
     */
    public void initialize(ContractService contractService, ProductService productService, int threadPoolCapacity) {

        this.contractService = contractService;
        this.productService = productService;
        this.threadPoolCapacity = threadPoolCapacity;

        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolCapacity);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        AtomicBoolean isContractCompleted = new AtomicBoolean(false);
        Runnable contractPerformance = () -> {

            try {
                List<Contract> contractList = contractService.getAll();

                contractList.forEach(contract -> {
                    ContractStatus contractStatus = contract.getStatus();
                    switch (contractStatus) {
                        case PAYED:
                            try {
                                updateContractStatus(contract, ContractStatus.IN_PROGRESS);

                                Long productId = contract.getProductId();
                                int quantity = contract.getQuantity();

                                updateQueueTime(productId, quantity);

                                for (int i = 0; i < quantity; i++) {
                                    double productionTime = productService.calculateProcessMinutes(productId);
                                    int currentPart = i + 1;
                                    processPart(contract, productionTime, currentPart);
                                }

                            } catch (DAOException exception) {
                                LOGGER.error("An exception is occurred during putting a contract in a queue " +
                                        "of the contract handler");
                            }
                            break;

                        case IN_PROGRESS:
                            if (!isContractCompleted.get()) {
                                try {
                                    Long contractId = contract.getId();
                                    Progress progress = contractService.getProgress(contractId);
                                    int executionPercentages = progress.getStatus();

                                    if (executionPercentages < 100) {
                                        Long productId = contract.getProductId();
                                        double productionTime = productService.calculateProcessMinutes(productId);
                                        int currentPart = calculateCurrentPartNumber(contract);
                                        int remainingQuantity = calculateRemainingQuantity(contract);
                                        int quantity = contract.getQuantity();

                                        updateCompletionDate(contract, remainingQuantity);
                                        updateQueueTime(productId, remainingQuantity);

                                        while (currentPart <= quantity) {
                                            processPart(contract, productionTime, currentPart);
                                            currentPart++;
                                        }

                                    } else {
                                        updateContractStatus(contract, ContractStatus.DONE);
                                    }

                                } catch (DAOException exception) {
                                    LOGGER.error("An exception is occurred during putting a contract in a queue " +
                                            "of the contract handler");
                                }
                            }
                            break;
                    }
                });
                isContractCompleted.set(true);

            } catch (DAOException exception) {
                LOGGER.error("An exception is occurred during selection all contracts");
            }
        };

        scheduledExecutorService.scheduleWithFixedDelay(contractPerformance, 0, 10, TimeUnit.SECONDS);
        LOGGER.info("Contract executor is initialized");
    }

    /**
     * Calculation current part number from contract execution progress
     *
     * @param contract - contract
     * @return part number
     */
    private int calculateCurrentPartNumber(Contract contract) throws DAOException {

        Progress progress = contractService.getProgress(contract.getId());
        int executionPercentages = progress.getStatus();
        int quantity = contract.getQuantity();
        int finishedParts = quantity * executionPercentages / 100;

        return finishedParts + 1;
    }

    /**
     * Calculation remaining parts quantity fro completion contract
     *
     * @param contract - contract
     * @return part quantity
     */
    private int calculateRemainingQuantity(Contract contract) throws DAOException {

        Progress progress = contractService.getProgress(contract.getId());
        double executionPercentages = progress.getStatus();
        int quantity = contract.getQuantity();
        int finishedParts = (int) Math.ceil(quantity * executionPercentages / 100);

        return quantity - finishedParts;
    }

    /**
     * Updating completion date of the contract
     *
     * @param contract          - contract
     * @param remainingQuantity - remaining parts quantity
     */
    private void updateCompletionDate(Contract contract, int remainingQuantity) throws DAOException {

        Long productId = contract.getProductId();
        Calendar completionDate = contractService.calculateCompletionDate
                (Calendar.getInstance(), productId, remainingQuantity);

        contract.setCompletionDate(completionDate);
        contractService.update(contract);
    }

    /**
     * Updating contract status
     *
     * @param contract - contract
     * @param status   - contract status
     */
    private void updateContractStatus(Contract contract, ContractStatus status) throws DAOException {

        contract.setStatus(status);
        contractService.update(contract);
    }

    /**
     * Part processing
     *
     * @param contract       - contract
     * @param productionTime - production time
     * @param currentPart    - current part for processing
     */
    private void processPart(Contract contract, double productionTime, int currentPart) {

        executorService.submit(() -> {
            try {
                LOGGER.info("Contract:{}. Executor is starting execution part number {} out of {}.",
                        contract.getId(), currentPart, contract.getQuantity());

                Thread.sleep((long) (productionTime * 60 * 1000));

                queueTime.addAndGet((long) -productionTime / threadPoolCapacity);
                LOGGER.info("Executor's queue time is {} minutes", queueTime);

                updateContractProgress(contract);

            } catch (InterruptedException | DAOException e) {
                LOGGER.error("Contract id={}: Processing of the part number {} is interrupted",
                        contract.getId(), currentPart);
            }
        });
    }

    /**
     * Destroying Executor
     */
    public void destroy() {

        queueTime.set(0);
        scheduledExecutorService.shutdownNow();
        executorService.shutdownNow();
        LOGGER.info("Contract executor is destroyed");
    }

    /**
     * Updating queue time
     *
     * @param productId - product id
     * @param quantity  - product quantity
     */
    private void updateQueueTime(Long productId, int quantity) throws DAOException {

        double productTimeInMinutes = productService.calculateProcessMinutes(productId);
        double contractExecutionTime = productTimeInMinutes * quantity / threadPoolCapacity;

        queueTime.addAndGet((long) contractExecutionTime);
        LOGGER.info("Executor's queue time is {} minutes", queueTime);
    }

    /**
     * Updating progress of the contract
     *
     * @param contract - contract
     */
    private void updateContractProgress(Contract contract) throws DAOException {

        CONTRACT_LOCK.lock();
        try {
            int quantity = contract.getQuantity();

            Progress currentProgress = contractService.getProgress(contract.getId());
            double executionPercentages = currentProgress.getStatus();

            int currentPart = (int) Math.ceil(quantity * executionPercentages / 100);
            currentPart++;

            int executionPercentage = currentPart * 100 / quantity;

            LOGGER.info("Contract:{}. Executor is finished {} parts out of {}", contract.getId(), currentPart, quantity);

            String log = String.format("Are finished %d part(s) out of %d", currentPart, quantity);
            Progress progress = new Progress(contract.getProgressId(), executionPercentage, log);

            contractService.updateProgress(progress);

            if (executionPercentage >= 100) {
                updateContractStatus(contract, ContractStatus.DONE);
                LOGGER.info("Contract id={} is done", contract.getId());

            } else {
                updateContractStatus(contract, ContractStatus.IN_PROGRESS);
            }

        } finally {
            CONTRACT_LOCK.unlock();
        }
    }
}
