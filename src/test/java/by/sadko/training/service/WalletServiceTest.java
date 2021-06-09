package by.sadko.training.service;

import by.sadko.training.AppContext;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.Wallet;
import by.sadko.training.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class WalletServiceTest {

    private final static Logger LOGGER = LogManager.getLogger(WalletServiceTest.class);
    private static final AppContext CONTEXT = AppContext.getInstance();
    private static UserAccountService accountService;
    private static WalletService walletService;
    private Long accountId;
    private UserAccount testingAccount;

    @BeforeClass
    public static void setUp() {

        CONTEXT.initialize();
        accountService = CONTEXT.getBean(UserAccountService.class);
        walletService = CONTEXT.getBean(WalletService.class);
    }

    @AfterClass
    public static void destroy() {

        CONTEXT.destroy();
    }

    @Before
    public void initAccount() throws DAOException {

        testingAccount = new UserAccount("karl", "karl@gmail.com", "karl123");
        accountId = accountService.signUp(testingAccount);
    }

    @After
    public void destroyAccount() throws DAOException {

        accountService.deleteAccount(accountId);
    }

    @Test
    public void shouldIncreaseAccountBalance() throws DAOException {

        Long walletID = testingAccount.getWalletId();

        Wallet wallet = walletService.getById(walletID);
        BigDecimal startingBalance = wallet.getBalance();
        LOGGER.info("Starting balance is {}", startingBalance);

        walletService.increaseBalance(testingAccount.getId(), new BigDecimal(100));

        wallet = walletService.getById(walletID);
        BigDecimal currentBalance = wallet.getBalance();
        LOGGER.info("Balance after increasing is {}", currentBalance);

        Assert.assertEquals(startingBalance.add(new BigDecimal(100)), currentBalance);
    }

    @Test
    public void shouldReduceAccountBalance() throws DAOException {

        Long walletID = testingAccount.getWalletId();

        walletService.increaseBalance(testingAccount.getId(), new BigDecimal(100));

        Wallet wallet = walletService.getById(walletID);
        BigDecimal startingBalance = wallet.getBalance();
        LOGGER.info("Starting balance is {}", startingBalance);

        walletService.reduceBalance(testingAccount.getId(), new BigDecimal(100));

        wallet = walletService.getById(walletID);
        BigDecimal currentBalance = wallet.getBalance();
        LOGGER.info("Balance after reducing is {}", currentBalance);

        Assert.assertEquals(startingBalance.subtract(new BigDecimal(100)), currentBalance);
    }
}
