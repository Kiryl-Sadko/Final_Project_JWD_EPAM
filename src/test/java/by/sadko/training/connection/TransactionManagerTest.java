package by.sadko.training.connection;

import by.sadko.training.AppContext;
import by.sadko.training.entity.UserAccount;
import by.sadko.training.entity.UserRole;
import by.sadko.training.exception.ConnectionException;
import by.sadko.training.exception.DAOException;
import by.sadko.training.service.UserAccountService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;

public class TransactionManagerTest {

    private static final AppContext context = AppContext.getInstance();
    private static final UserAccount userAccount = new UserAccount();
    private static UserAccountService accountService;

    @BeforeClass
    public static void setUp() {

        context.initialize();
        accountService = context.getBean(UserAccountService.class);
    }

    @AfterClass
    public static void shutDown() throws DAOException {

        accountService.deleteAccount(userAccount.getId());
        context.destroy();
    }

    @Test
    public void shouldBeginAndCommitTransaction() throws DAOException, ConnectionException {

        TransactionManager transactionManager = context.getBean(BasicTransactionManager.class);
        TransactionManager spyTransactionManager = Mockito.spy(transactionManager);
        UserAccountService proxyAccountService = createProxy(spyTransactionManager, accountService, UserAccountService.class);

        userAccount.setEmail("test@gmail.com");
        userAccount.setName("test");
        userAccount.setPassword("Test123");

        ArrayList<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.CUSTOMER);
        userAccount.setUserRoles(userRoles);

        proxyAccountService.signUp(userAccount);

        Mockito.verify(spyTransactionManager, Mockito.times(1)).begin();
        Mockito.verify(spyTransactionManager, Mockito.times(1)).commit();
    }

    private <T> T createProxy(TransactionManager trSpy, T accountService, Class<T>... interfaces) {

        InvocationHandler transactionalInvocationHandler = AppContext
                .createTransactionalInvocationHandler(trSpy, accountService);

        return AppContext.createProxy(this.getClass().getClassLoader(),
                transactionalInvocationHandler, interfaces);
    }
}
