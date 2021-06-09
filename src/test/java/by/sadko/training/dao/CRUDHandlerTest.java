package by.sadko.training.dao;

import by.sadko.training.connection.BasicConnectionManager;
import by.sadko.training.connection.BasicConnectionPool;
import by.sadko.training.connection.BasicTransactionManager;
import by.sadko.training.connection.ConnectionPool;
import by.sadko.training.entity.Customer;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLCustomerParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class CRUDHandlerTest {

    private static final String SELECT_ALL = "SELECT * FROM customer";
    private static final String SELECT_BY_ID = "SELECT * FROM customer WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM customer WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO customer (customer_name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE customer SET customer_name=? WHERE id=?";

    private static final Logger LOGGER = LogManager.getLogger(CRUDHandlerTest.class);

    private static CRUDHandler<Customer> crudHandler;
    private static ConnectionPool<Connection> connectionPool;
    private Customer testingCustomer;

    @BeforeClass
    public static void setUp() {

        connectionPool = BasicConnectionPool.getInstance();
        BasicTransactionManager transactionManager = new BasicTransactionManager(connectionPool);
        BasicConnectionManager connectionManager = new BasicConnectionManager(connectionPool, transactionManager);
        SQLCustomerParser customerParser = new SQLCustomerParser();

        crudHandler = new CRUDHandler<>(connectionManager, customerParser);
    }

    @AfterClass
    public static void destroy() {

        connectionPool.shutdown();
    }

    @Before
    public void initCustomer() {

        testingCustomer = new Customer("Test");
    }

    @After
    public void destroyCustomer() throws DAOException {

        if (testingCustomer != null) {
            crudHandler.delete(DELETE_QUERY, testingCustomer);
        }
        LOGGER.info("Testing customer is destroyed from data base");
    }

    @Test
    public void shouldCreateEntity() throws DAOException {

        Long customerId = crudHandler.create(INSERT_QUERY, testingCustomer);
        LOGGER.info("Customer is created, id={}", customerId);

        Assert.assertNotNull(customerId);
    }

    @Test
    public void shouldFindEntityById() throws DAOException {

        Long customerId = crudHandler.create(INSERT_QUERY, testingCustomer);

        Customer selectedCustomer = crudHandler.findById(SELECT_BY_ID, customerId);
        LOGGER.info("Customer '{}' is selected from data base", selectedCustomer.getName());

        Assert.assertEquals(testingCustomer, selectedCustomer);
    }

    @Test
    public void shouldFindEntityList() throws DAOException {

        Long customerId = crudHandler.create(INSERT_QUERY, testingCustomer);

        List<Customer> customerList = crudHandler.findAll(SELECT_ALL);
        int listSize = customerList.size();
        LOGGER.info("Amount of the selected customers is {}", listSize);

        Assert.assertTrue(listSize > 0);
    }

    @Test
    public void shouldDeleteEntity() throws DAOException {

        Long customerId = crudHandler.create(INSERT_QUERY, testingCustomer);
        LOGGER.info("Customer is created, id={}", customerId);

        boolean isDeleted = crudHandler.delete(DELETE_QUERY, customerId);

        Assert.assertTrue(isDeleted);
        Assert.assertNull(crudHandler.findById(SELECT_BY_ID, customerId));
    }

    @Test
    public void shouldUpdateEntity() throws DAOException {

        Long customerId = crudHandler.create(INSERT_QUERY, testingCustomer);
        testingCustomer.setName("Updated");

        boolean isUpdated = crudHandler.update(UPDATE_QUERY, testingCustomer);
        Assert.assertTrue(isUpdated);

        Customer customer = crudHandler.findById(SELECT_BY_ID, customerId);
        Assert.assertEquals(testingCustomer.getName(), customer.getName());
    }
}
