package by.sadko.training.service.impl;

import by.sadko.training.connection.Transactional;
import by.sadko.training.dao.impl.BasicContractDao;
import by.sadko.training.dao.impl.BasicProgressDao;
import by.sadko.training.entity.Contract;
import by.sadko.training.entity.ContractStatus;
import by.sadko.training.entity.Progress;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.ContractExecutor;
import by.sadko.training.service.ContractService;
import by.sadko.training.service.CustomerService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BasicContractService implements ContractService {

    private static final Logger LOGGER = LogManager.getLogger(BasicContractService.class);

    private final BasicContractDao contractDAO;
    private final BasicProgressDao progressDAO;
    private final WalletService walletService;
    private final CustomerService customerService;
    private final ProductService productService;

    public BasicContractService(BasicContractDao contractDAO, BasicProgressDao progressDAO, WalletService walletService,
                                CustomerService customerService, ProductService productService) {
        this.contractDAO = contractDAO;
        this.progressDAO = progressDAO;
        this.walletService = walletService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    @Override
    public Long createContract(Long accountId, String customerName, Long productId, int productQuantity,
                               double profitRatio) throws DAOException {

        Long customerId = customerService.getByNameOrCreate(customerName);
        BigDecimal price = calculatePrice(productId, productQuantity, profitRatio);

        Calendar paymentDate = new GregorianCalendar();
        Calendar completionDate = calculateCompletionDate(paymentDate, productId, productQuantity);

        String progressLog = String.format("Finished %d parts out of %d", 0, productQuantity);
        Progress progress = new Progress(0, progressLog);
        Long progressId = progressDAO.create(progress);

        Contract result = new Contract(accountId, customerId, productId, productQuantity, price,
                paymentDate, completionDate, progressId);

        return contractDAO.create(result);
    }

    @Transactional
    @Override
    public boolean payContract(Long id) throws DAOException {

        Contract contract = contractDAO.findById(id);
        int productQuantity = contract.getQuantity();
        BigDecimal price = contract.getPrice();
        Long accountId = contract.getUserAccountID();
        Long productId = contract.getProductId();

        BigDecimal productCost = productService.calculateProductCost(productId);
        BigDecimal totalCost = productCost.multiply(BigDecimal.valueOf(productQuantity));
        BigDecimal profit = price.subtract(totalCost);

        boolean isReduced;
        boolean isIncreased;
        if (accountId != 1L) {
            isReduced = walletService.reduceBalance(accountId, price);
            isIncreased = walletService.increaseBalance(1L, profit);

        } else {
            isReduced = walletService.reduceBalance(accountId, totalCost);
            isIncreased = true;
        }

        if (isReduced && isIncreased) {

            Calendar paymentDate = new GregorianCalendar();
            Calendar completionDate = calculateCompletionDate(paymentDate, productId, productQuantity);
            contract.setPaymentDate(paymentDate);
            contract.setCompletionDate(completionDate);
            contract.setStatus(ContractStatus.PAYED);

            update(contract);
            contractDAO.update(contract);

            LOGGER.info("Payment is occurred successfully, completion date is updated");
            return true;
        }

        LOGGER.info("Payment isn't occurred");
        return false;
    }

    @Transactional
    @Override
    public boolean makeDiscount(Long contractId, double discount) throws DAOException {

        Contract contract = getById(contractId);
        BigDecimal price = contract.getPrice();
        double factor = 1 - discount;
        BigDecimal discountPrice = price.multiply(BigDecimal.valueOf(factor));
        discountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);

        contract.setPrice(discountPrice);
        LOGGER.info("Discount by contract id={} is made. Old price is {}, new price is {}",
                contractId, price, discountPrice);

        return update(contract);
    }

    @Transactional
    @Override
    public Progress getProgress(Long contractId) throws DAOException {

        Contract contract = getById(contractId);
        Long progressId = contract.getProgressId();
        return progressDAO.findById(progressId);
    }

    @Transactional
    @Override
    public Calendar calculateCompletionDate(Calendar paymentDate, Long productId, int quantity) throws DAOException {

        double productTimeInMinutes = productService.calculateProcessMinutes(productId);
        int threadPoolCapacity = ContractExecutor.getInstance().getThreadPoolCapacity();

        double contractExecutionTime = productTimeInMinutes * quantity / threadPoolCapacity;

        AtomicLong queueTime = ContractExecutor.getQueueTime();
        double queueTimeInMinutes = queueTime.get();

        double totalTimeInMinutes = queueTimeInMinutes + contractExecutionTime + 7;

        Calendar completionTime = (Calendar) paymentDate.clone();
        completionTime.add(Calendar.MINUTE, (int) totalTimeInMinutes);

        return completionTime;
    }

    private BigDecimal calculatePrice(Long productId, int productQuantity, double profit) throws DAOException {

        BigDecimal pieceCost = productService.calculateProductCost(productId);
        BigDecimal profitRatio = BigDecimal.valueOf(profit);
        BigDecimal quantity = BigDecimal.valueOf(productQuantity);

        return pieceCost.multiply(quantity).multiply(profitRatio);
    }

    @Override
    public List<Contract> getAll() throws DAOException {
        return contractDAO.findAll();
    }

    @Override
    public Contract getById(Long id) throws DAOException {
        return contractDAO.findById(id);
    }

    @Override
    public boolean update(Contract contract) throws DAOException {
        return contractDAO.update(contract);
    }

    @Override
    public boolean updateProgress(Progress progress) throws DAOException {

        if (progress.getStatus() > 100) {
            progress.setStatus(100);
        }
        return progressDAO.update(progress);
    }

    @Override
    public List<Contract> getByAccountId(Long accountId) throws DAOException {
        return contractDAO.findContractListByAccount(accountId);
    }

    @Override
    public boolean delete(Contract contract) throws DAOException {
        return contractDAO.delete(contract.getId());
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        return contractDAO.delete(id);
    }
}
