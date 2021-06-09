package by.sadko.training.service;

import by.sadko.training.AppContext;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.Wallet;
import by.sadko.training.exception.DAOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserAccountServiceTest {

    private static final AppContext CONTEXT = AppContext.getInstance();
    private static UserAccountService accountService;
    private static WalletService walletService;
    private Long accountId;
    private UserAccount testingAccount;

    @BeforeClass
    public static void setUp() throws DAOException {

        CONTEXT.initialize();
        accountService = CONTEXT.getBean(UserAccountService.class);
        walletService = CONTEXT.getBean(WalletService.class);
    }

    @AfterClass
    public static void destroy() throws DAOException {

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
    public void shouldReturnCheckedAccountByIdAndEmail() throws DAOException {

        String email = testingAccount.getEmail();
        String password = testingAccount.getPassword();

        UserAccount resultingAccount = accountService.login(email, password);
        Assert.assertEquals(testingAccount, resultingAccount);

        resultingAccount = accountService.login(email, "incorrect password");
        Assert.assertNotSame(testingAccount, resultingAccount);
    }

    @Test
    public void shouldCreateAccountWithWallet() throws DAOException {

        Long walletID = testingAccount.getWalletId();
        Wallet walletById = walletService.getById(walletID);

        Assert.assertEquals(testingAccount.getWalletId(), walletById.getId());
    }

    @Test
    public void shouldRemoveAccountWithWallet() throws DAOException {

        Long walletID = testingAccount.getWalletId();

        accountService.deleteAccount(accountId);

        UserAccount accountById = accountService.findById(accountId);
        Assert.assertNull(accountById);

        Wallet walletById = walletService.getById(walletID);
        Assert.assertNull(walletById);
    }
}
