package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.ContractDao;
import by.sadko.training.entity.Contract;
import by.sadko.training.exception.ConnectionException;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLContractParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class of the basic implementation ContractDAO
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ContractDao,SQLContractParser,CRUDHandler
 */
public class BasicContractDao implements ContractDao {

    private static final Logger LOGGER = LogManager.getLogger(BasicContractDao.class);

    private static final String SELECT_ALL = "SELECT * FROM contract";
    private static final String SELECT_BY_ID = "SELECT * FROM contract WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM contract WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO contract (user_account_id, customer_id, " +
            "product_id, contract_product_quantity, contract_price, contract_status, " +
            "contract_payment_date, contract_completion_date, progress_progress_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE contract SET user_account_id=?, customer_id=?, " +
            "product_id=?, contract_product_quantity=?, contract_price=?, contract_status=?,  " +
            "contract_payment_date=?, contract_completion_date=?, progress_progress_id=? WHERE id=?";
    private static final String SELECT_BY_ACCOUNT_ID = "SELECT * FROM contract WHERE user_account_id=?";

    private final ConnectionManager connectionManager;
    private final SQLContractParser contractParser;
    private final CRUDHandler<Contract> crudHandler;

    /**
     * Initializing of the class
     *
     * @param connectionManager - connection manager
     * @param contractParser    - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicContractDao(ConnectionManager connectionManager, SQLContractParser contractParser) {
        this.connectionManager = connectionManager;
        this.contractParser = contractParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, contractParser);
    }

    /**
     * Searching contract list by userAccount id
     *
     * @param accountId - userAccount id
     * @return list of the contracts
     */
    @Override
    public List<Contract> findContractListByAccount(Long accountId) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ACCOUNT_ID)) {

            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return contractParser.parseResultSetToEntityList(resultSet);

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during contract's selection by account id");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Selection all contracts from data base
     *
     * @return contract list
     */
    @Override
    public List<Contract> findAll() throws DAOException {

        return crudHandler.findAll(SELECT_ALL);
    }

    /**
     * Selection contract by id
     *
     * @param contractId - contract id
     * @return - contract
     */
    @Override
    public Contract findById(Long contractId) throws DAOException {

        return crudHandler.findById(SELECT_BY_ID, contractId);
    }

    /**
     * Deletion contract from data base by id
     *
     * @param contractId - contract id
     * @return boolean result of the deletion
     */
    @Override
    public boolean delete(Long contractId) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, contractId);
    }

    /**
     * Insertion contract to data base
     *
     * @param contract - contract
     * @return contract id from data base
     */
    @Override
    public Long create(Contract contract) throws DAOException {

        return crudHandler.create(INSERT_QUERY, contract);
    }

    /**
     * Updating contract in data base
     *
     * @param contract - contract
     * @return boolean result of hte updating
     */
    @Override
    public boolean update(Contract contract) throws DAOException {

        return crudHandler.update(UPDATE_QUERY, contract);
    }
}
