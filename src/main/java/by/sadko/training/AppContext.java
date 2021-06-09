package by.sadko.training;

import by.sadko.training.command.AppCommand;
import by.sadko.training.command.AppCommandProvider;
import by.sadko.training.command.BasicCommandProvider;
import by.sadko.training.command.impl.CreateContractCommand;
import by.sadko.training.command.impl.CreateMaterialCommand;
import by.sadko.training.command.impl.CreateProductCommand;
import by.sadko.training.command.impl.DeleteContractCommand;
import by.sadko.training.command.impl.DeleteMaterialCommand;
import by.sadko.training.command.impl.DeleteProductCommand;
import by.sadko.training.command.impl.DisplayContractCreationCommand;
import by.sadko.training.command.impl.DisplayContractListCommand;
import by.sadko.training.command.impl.DisplayMaterialEditionCommand;
import by.sadko.training.command.impl.DisplayMaterialListCommand;
import by.sadko.training.command.impl.DisplayProductCreationCommand;
import by.sadko.training.command.impl.DisplayProductEditionCommand;
import by.sadko.training.command.impl.DisplayProductListCommand;
import by.sadko.training.command.impl.DisplayWorkloadCommand;
import by.sadko.training.command.impl.EditMaterialCommand;
import by.sadko.training.command.impl.EditProductCommand;
import by.sadko.training.command.impl.LogInCommand;
import by.sadko.training.command.impl.MakeDiscountCommand;
import by.sadko.training.command.impl.PayContractCommand;
import by.sadko.training.command.impl.RefillWalletCommand;
import by.sadko.training.command.impl.SignUpCommand;
import by.sadko.training.connection.BasicConnectionManager;
import by.sadko.training.connection.BasicConnectionPool;
import by.sadko.training.connection.BasicTransactionManager;
import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.connection.ConnectionPool;
import by.sadko.training.connection.TransactionManager;
import by.sadko.training.connection.Transactional;
import by.sadko.training.dao.impl.BasicContractDao;
import by.sadko.training.dao.impl.BasicCustomerDao;
import by.sadko.training.dao.impl.BasicMaterialDao;
import by.sadko.training.dao.impl.BasicOperationDao;
import by.sadko.training.dao.impl.BasicProductDao;
import by.sadko.training.dao.impl.BasicProgressDao;
import by.sadko.training.dao.impl.BasicTechnologicalProcessDao;
import by.sadko.training.dao.impl.BasicUserAccountDao;
import by.sadko.training.dao.impl.BasicWalletDao;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLAccountParser;
import by.sadko.training.parser.SQLContractParser;
import by.sadko.training.parser.SQLCustomerParser;
import by.sadko.training.parser.SQLMaterialParser;
import by.sadko.training.parser.SQLOperationParser;
import by.sadko.training.parser.SQLProductParser;
import by.sadko.training.parser.SQLProgressParser;
import by.sadko.training.parser.SQLTechnologicalProcessParser;
import by.sadko.training.parser.SQLWalletParser;
import by.sadko.training.service.ContractService;
import by.sadko.training.service.CustomerService;
import by.sadko.training.service.MaterialService;
import by.sadko.training.service.OperationService;
import by.sadko.training.service.ProductService;
import by.sadko.training.service.TechnologicalProcessService;
import by.sadko.training.service.UserAccountService;
import by.sadko.training.service.WalletService;
import by.sadko.training.service.impl.BasicContractService;
import by.sadko.training.service.impl.BasicCustomerService;
import by.sadko.training.service.impl.BasicMaterialService;
import by.sadko.training.service.impl.BasicOperationService;
import by.sadko.training.service.impl.BasicProductService;
import by.sadko.training.service.impl.BasicTechnologicalProcessService;
import by.sadko.training.service.impl.BasicUserAccountService;
import by.sadko.training.service.impl.BasicWalletService;
import by.sadko.training.validation.BeanValidator;
import by.sadko.training.validation.Email;
import by.sadko.training.validation.FieldValidator;
import by.sadko.training.validation.MaxLength;
import by.sadko.training.validation.MinLength;
import by.sadko.training.validation.Password;
import by.sadko.training.validation.impl.AnnotationBasedBeanValidator;
import by.sadko.training.validation.impl.EmailFieldValidator;
import by.sadko.training.validation.impl.MaxLengthFieldValidator;
import by.sadko.training.validation.impl.MinLengthFieldValidator;
import by.sadko.training.validation.impl.PasswordFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.sadko.training.command.AppCommandName.*;

/**
 * Application context
 * Should be a singleton
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public class AppContext {

    private static final Logger LOGGER = LogManager.getLogger(AppContext.class);
    private static final AtomicBoolean IS_INSTANCE_EXIST = new AtomicBoolean(false);
    private static final Lock INSTANCE_LOCK = new ReentrantLock();

    private static AppContext instance;
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private ConnectionPool<Connection> connectionPool;

    private AppContext() {
    }

    public static AppContext getInstance() {
        if (!IS_INSTANCE_EXIST.get()) {
            INSTANCE_LOCK.lock();

            try {
                if (instance == null) {
                    instance = new AppContext();
                    IS_INSTANCE_EXIST.set(true);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return instance;
    }

    /**
     * Creation invocation handler of the transactional methods
     *
     * @param transactionManager - transaction manager
     * @param service            - service
     * @param <T>                - service type
     * @return method result
     */
    public static <T> InvocationHandler createTransactionalInvocationHandler(TransactionManager transactionManager,
                                                                             T service) {
        return (proxy, method, args) -> {

            Method declaredMethod = service.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (method.isAnnotationPresent(Transactional.class)
                    || declaredMethod.isAnnotationPresent(Transactional.class)) {

                transactionManager.begin();
                try {
                    Object result = method.invoke(service, args);
                    transactionManager.commit();
                    return result;

                } catch (Exception e) {
                    LOGGER.error("Transaction will be rolled back, exception is occurred " +
                            "during methodName={} execution", method.getName());

                    transactionManager.rollback();
                    throw new DAOException();
                }

            } else {
                return method.invoke(service, args);
            }
        };
    }

    /**
     * Creation proxy of a service
     *
     * @param classLoader - class loader
     * @param handler     - transactional invocation handler
     * @param toBeProxied - service
     * @return proxy
     */
    public static <T> T createProxy(ClassLoader classLoader, InvocationHandler handler, Class<T>... toBeProxied) {

        return (T) Proxy.newProxyInstance(classLoader, toBeProxied, handler);
    }

    /**
     * Initializing of the application context
     */
    public void initialize() {

        connectionPool = BasicConnectionPool.getInstance();
        TransactionManager transactionManager = new BasicTransactionManager(connectionPool);
        ConnectionManager connectionManager = new BasicConnectionManager(connectionPool, transactionManager);

        //Initialization DAO-layer
        BasicUserAccountDao userAccountDAO = new BasicUserAccountDao(connectionManager, new SQLAccountParser());
        BasicWalletDao walletDAO = new BasicWalletDao(connectionManager, new SQLWalletParser());
        BasicTechnologicalProcessDao technologicalProcessDAO =
                new BasicTechnologicalProcessDao(connectionManager, new SQLTechnologicalProcessParser());
        BasicProgressDao progressDAO = new BasicProgressDao(connectionManager, new SQLProgressParser());
        BasicProductDao productDAO = new BasicProductDao(connectionManager, new SQLProductParser());
        BasicMaterialDao materialDAO = new BasicMaterialDao(connectionManager, new SQLMaterialParser());
        BasicCustomerDao customerDAO = new BasicCustomerDao(connectionManager, new SQLCustomerParser());
        BasicContractDao contractDAO = new BasicContractDao(connectionManager, new SQLContractParser());
        BasicOperationDao operationDAO = new BasicOperationDao(connectionManager, new SQLOperationParser());

        //Initialization service-layer
        UserAccountService userAccountService = new BasicUserAccountService(userAccountDAO, walletDAO);
        WalletService walletService = new BasicWalletService(walletDAO, userAccountDAO);
        ProductService productService = new BasicProductService(productDAO,
                materialDAO, technologicalProcessDAO);
        CustomerService customerService = new BasicCustomerService(customerDAO);
        ContractService contractService = new BasicContractService(contractDAO, progressDAO, walletService,
                customerService, productService);
        MaterialService materialService = new BasicMaterialService(materialDAO);
        OperationService operationService = new BasicOperationService(operationDAO);
        TechnologicalProcessService processService = new BasicTechnologicalProcessService(technologicalProcessDAO);

        //Initialization of the validator container
        Map<Class<? extends Annotation>, FieldValidator> validatorMap = new HashMap<>();
        validatorMap.put(MaxLength.class, new MaxLengthFieldValidator());
        validatorMap.put(MinLength.class, new MinLengthFieldValidator());
        validatorMap.put(Email.class, new EmailFieldValidator());
        validatorMap.put(Password.class, new PasswordFieldValidator());
        BeanValidator beanValidator = new AnnotationBasedBeanValidator(validatorMap);

        //Initialization of transactional invocation handlers
        InvocationHandler accountServiceHandler = createTransactionalInvocationHandler
                (transactionManager, userAccountService);
        UserAccountService accountProxyService = createProxy(getClass().getClassLoader(),
                accountServiceHandler, UserAccountService.class);

        InvocationHandler walletServiceHandler = createTransactionalInvocationHandler
                (transactionManager, walletService);
        WalletService walletProxyService = createProxy
                (getClass().getClassLoader(), walletServiceHandler, WalletService.class);

        InvocationHandler productServiceHandler = createTransactionalInvocationHandler
                (transactionManager, productService);
        ProductService productProxyService = createProxy
                (getClass().getClassLoader(), productServiceHandler, ProductService.class);

        InvocationHandler contractServiceHandler = createTransactionalInvocationHandler
                (transactionManager, contractService);
        ContractService contractProxyService = createProxy
                (getClass().getClassLoader(), contractServiceHandler, ContractService.class);

        InvocationHandler customerServiceHandler = createTransactionalInvocationHandler
                (transactionManager, customerService);
        CustomerService customerProxyService = createProxy
                (getClass().getClassLoader(), customerServiceHandler, CustomerService.class);

        InvocationHandler materialServiceHandler = createTransactionalInvocationHandler
                (transactionManager, materialService);
        MaterialService materialProxyService = createProxy
                (getClass().getClassLoader(), materialServiceHandler, MaterialService.class);

        InvocationHandler operationServiceHandler = createTransactionalInvocationHandler
                (transactionManager, operationService);
        OperationService operationProxyService = createProxy
                (getClass().getClassLoader(), operationServiceHandler, OperationService.class);

        InvocationHandler processServiceHandler = createTransactionalInvocationHandler
                (transactionManager, processService);
        TechnologicalProcessService processProxyService = createProxy
                (getClass().getClassLoader(), processServiceHandler, TechnologicalProcessService.class);


        //Initialization of the commands
        AppCommand loginCommand = new LogInCommand(accountProxyService);
        AppCommand refillWalletCommand = new RefillWalletCommand(walletProxyService);
        AppCommand displayContractListCommand =
                new DisplayContractListCommand(contractProxyService, customerProxyService, productProxyService);
        AppCommand displayCreationCommand = new DisplayContractCreationCommand(productProxyService);
        AppCommand createContractCommand = new CreateContractCommand(contractProxyService);
        AppCommand payContractCommand = new PayContractCommand(contractProxyService);
        AppCommand signUpCommand = new SignUpCommand(accountProxyService, beanValidator);
        AppCommand deleteContractCommand = new DeleteContractCommand(contractProxyService);
        AppCommand displayWorkloadCommand = new DisplayWorkloadCommand(accountProxyService, contractProxyService,
                customerProxyService, productProxyService);
        AppCommand makeDiscountCommand = new MakeDiscountCommand(contractProxyService);
        AppCommand displayProductListCommand = new DisplayProductListCommand(productProxyService, materialProxyService,
                processProxyService);
        AppCommand displayProductCreationCommand = new DisplayProductCreationCommand(materialProxyService,
                operationProxyService);
        AppCommand createProductCommand = new CreateProductCommand(productProxyService, processProxyService,
                operationProxyService);
        AppCommand deleteProductCommand = new DeleteProductCommand(productProxyService);
        AppCommand displayProductEditionCommand = new DisplayProductEditionCommand(productProxyService, materialProxyService,
                processProxyService, operationProxyService);
        AppCommand editProductCommand = new EditProductCommand(productProxyService, operationProxyService,
                processProxyService);
        AppCommand displayMaterialListCommand = new DisplayMaterialListCommand(materialProxyService);
        AppCommand deleteMaterialCommand = new DeleteMaterialCommand(materialProxyService);
        AppCommand createMaterialCommand = new CreateMaterialCommand(materialProxyService);
        AppCommand displayMaterialEditionCommand = new DisplayMaterialEditionCommand(materialProxyService);
        AppCommand editMaterialCommand = new EditMaterialCommand(materialProxyService);

        //Initialization of the command provider
        AppCommandProvider commandProvider = new BasicCommandProvider();
        commandProvider.registerCommand(LOGIN_DISPLAY, (request, response) -> "login_view");
        commandProvider.registerCommand(COMMAND_NOT_FOUND, (request, response) -> "");
        commandProvider.registerCommand(WALLET_DISPLAY, (request, response) -> "wallet_view");
        commandProvider.registerCommand(LOGIN_SUBMIT, loginCommand);
        commandProvider.registerCommand(INDEX, (request, response) -> "index");
        commandProvider.registerCommand(WALLET_REFILL_BALANCE, refillWalletCommand);
        commandProvider.registerCommand(CONTRACT_DISPLAY_ALL, displayContractListCommand);
        commandProvider.registerCommand(CONTRACT_DISPLAY_CREATION, displayCreationCommand);
        commandProvider.registerCommand(CONTRACT_CREATE, createContractCommand);
        commandProvider.registerCommand(CONTRACT_PAY, payContractCommand);
        commandProvider.registerCommand(LOGOUT, (request, response) -> {
            SecurityContext.getInstance().logOut();
            return "";
        });
        commandProvider.registerCommand(NO_PERMISSION, (request, response) -> "no_permission_view");
        commandProvider.registerCommand(SIGN_UP_DISPLAY, (request, response) -> "sign_up_view");
        commandProvider.registerCommand(DISPLAY_ABOUT_US, (request, response) -> "about_us_view");
        commandProvider.registerCommand(SIGN_UP_SUBMIT, signUpCommand);
        commandProvider.registerCommand(CONTRACT_DELETE, deleteContractCommand);
        commandProvider.registerCommand(WORKLOAD_DISPLAY_ALL, displayWorkloadCommand);
        commandProvider.registerCommand(CONTRACT_MAKE_DISCOUNT, makeDiscountCommand);
        commandProvider.registerCommand(PRODUCT_DISPLAY_CREATION, displayProductCreationCommand);
        commandProvider.registerCommand(PRODUCT_DISPLAY_ALL, displayProductListCommand);
        commandProvider.registerCommand(PRODUCT_CREATE, createProductCommand);
        commandProvider.registerCommand(PRODUCT_DELETE, deleteProductCommand);
        commandProvider.registerCommand(PRODUCT_DISPLAY_EDITION, displayProductEditionCommand);
        commandProvider.registerCommand(PRODUCT_EDIT, editProductCommand);
        commandProvider.registerCommand(MATERIAL_DISPLAY_ALL, displayMaterialListCommand);
        commandProvider.registerCommand(MATERIAL_DELETE, deleteMaterialCommand);
        commandProvider.registerCommand(MATERIAL_DISPLAY_CREATION, ((request, response) -> "material_creation_view"));
        commandProvider.registerCommand(MATERIAL_CREATE, createMaterialCommand);
        commandProvider.registerCommand(MATERIAL_DISPLAY_EDITION, displayMaterialEditionCommand);
        commandProvider.registerCommand(MATERIAL_EDIT, editMaterialCommand);


        //Filling of the context container
        beans.put(AppCommandProvider.class, commandProvider);

        beans.put(WalletService.class, walletService);
        beans.put(ProductService.class, productService);
        beans.put(ContractService.class, contractService);
        beans.put(UserAccountService.class, userAccountService);

        beans.put(BasicTransactionManager.class, transactionManager);
        beans.put(ConnectionManager.class, connectionManager);

        LOGGER.info("Application context is initialized");
    }

    /**
     * Destroying of the application context
     */
    public void destroy() {

        connectionPool.shutdown();
        beans.clear();
        LOGGER.info("Application context is destroyed");
    }

    /**
     * Returns beans of the application context
     *
     * @param clazz - class of the bean
     * @param <T>   - bean type
     * @return bean
     */
    public <T> T getBean(Class<T> clazz) {
        return (T) this.beans.get(clazz);
    }
}
