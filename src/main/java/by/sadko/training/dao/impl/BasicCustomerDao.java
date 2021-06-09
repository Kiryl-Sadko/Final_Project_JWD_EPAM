package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.CustomerDao;
import by.sadko.training.entity.Customer;
import by.sadko.training.exception.ConnectionException;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLCustomerParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class of the basic implementation CustomerDAO
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see CustomerDao,SQLCustomerParser,CRUDHandler
 */
public class BasicCustomerDao implements CustomerDao {

    private static final Logger LOGGER = LogManager.getLogger(BasicCustomerDao.class);

    private static final String SELECT_ALL = "SELECT * FROM customer";
    private static final String SELECT_BY_ID = "SELECT * FROM customer WHERE id=?";
    private static final String SELECT_BY_NAME = "SELECT * FROM customer WHERE customer_name=?";
    private static final String DELETE_QUERY = "DELETE FROM customer WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO customer (customer_name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE customer SET customer_name=? WHERE id=?";

    private final ConnectionManager connectionManager;
    private final SQLCustomerParser customerParser;
    private final CRUDHandler<Customer> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager - connection manager
     * @param customerParser    - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicCustomerDao(ConnectionManager connectionManager, SQLCustomerParser customerParser) {
        this.connectionManager = connectionManager;
        this.customerParser = customerParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, customerParser);
    }

    /**
     * Searching customer from data base by name
     *
     * @param customerName - customer name
     * @return customer
     */
    @Override
    public Customer findByName(String customerName) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {

            preparedStatement.setString(1, customerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Customer> customers = customerParser.parseResultSetToEntityList(resultSet);

            if (customers.isEmpty()) {
                return null;
            } else {
                return customers.get(0);
            }

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during selection customer by name");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Selection all customers from data base
     *
     * @return list of the customers
     */
    @Override
    public List<Customer> findAll() throws DAOException {

        return crudHandler.findAll(SELECT_ALL);
    }

    /**
     * Selection customer by id
     *
     * @param customerId - customer id
     * @return customer
     */
    @Override
    public Customer findById(Long customerId) throws DAOException {

        return crudHandler.findById(SELECT_BY_ID, customerId);
    }

    /**
     * Deletion customer from data base by id
     *
     * @param customerId - customer id
     * @return boolean result of the deletion
     */
    @Override
    public boolean delete(Long customerId) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, customerId);
    }

    /**
     * Insertion customer to data base
     *
     * @param customer - customer
     * @return customer id from data base
     */
    @Override
    public Long create(Customer customer) throws DAOException {

        return crudHandler.create(INSERT_QUERY, customer);
    }

    /**
     * Updating customer in data base
     *
     * @param customer - customer
     * @return boolean result of the updating
     */
    @Override
    public boolean update(Customer customer) throws DAOException {
        return crudHandler.update(UPDATE_QUERY, customer);
    }
}
