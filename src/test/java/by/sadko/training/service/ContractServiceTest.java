package by.sadko.training.service;

import by.sadko.training.AppContext;
import by.sadko.training.entity.Contract;
import by.sadko.training.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

public class ContractServiceTest {

    private static final Logger LOGGER = LogManager.getLogger(ContractServiceTest.class);

    private static final AppContext CONTEXT = AppContext.getInstance();
    private static ContractService contractService;
    private static Long contractId;

    @BeforeClass
    public static void setUp() throws DAOException {

        CONTEXT.initialize();
        contractService = CONTEXT.getBean(ContractService.class);
        contractId = contractService.createContract
                (2L, "Reno", 2L, 3, 1.2);
    }

    @AfterClass
    public static void destroy() throws DAOException {

        contractService.delete(contractId);
        CONTEXT.destroy();
    }

    @Test
    public void shouldCreateContract() throws DAOException {

        Contract contract = contractService.getById(contractId);
        LOGGER.info(contract.toString());

        Assert.assertNotNull(contract);
    }

    @Test
    public void contractShouldBePayed() throws DAOException {

        Contract contract = contractService.getById(contractId);
        LOGGER.info("Before payment: Contract's parameters: status={}", contract.getStatus());

        boolean isPayed = contractService.payContract(contractId);

        contract = contractService.getById(contractId);
        LOGGER.info("After payment: Contract's parameters: status={}", contract.getStatus());

        Assert.assertTrue(isPayed);
    }

    @Test
    public void shouldReducePrice() throws DAOException {

        Contract contract = contractService.getById(contractId);
        BigDecimal startingPrice = contract.getPrice();

        contractService.makeDiscount(contractId, 0.5);

        contract = contractService.getById(contractId);
        BigDecimal discountPrice = contract.getPrice();

        Assert.assertTrue(startingPrice.compareTo(discountPrice) > 0);
    }

    @Test
    public void shouldCalculateCompletionTime() throws DAOException {

        Contract contract = contractService.getById(contractId);
        Calendar paymentDate = contract.getPaymentDate();
        Long productId = contract.getProductId();
        int quantity = contract.getQuantity();

        Calendar completionDate = contractService.calculateCompletionDate(paymentDate, productId, quantity);

        LOGGER.info("Payment date of the contract id={} is {}. Completion date of the contract is {}",
                contractId, paymentDate.getTime(), completionDate.getTime());

        Assert.assertTrue(completionDate.compareTo(paymentDate) > 0);
    }
}
