package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.OperationDao;
import by.sadko.training.entity.Operation;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLOperationParser;

import java.util.List;

/**
 * Class of the basic implementation Operation Dao
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see OperationDao,SQLOperationParser,CRUDHandler
 */
public class BasicOperationDao implements OperationDao {

    private static final String SELECT_ALL = "SELECT * FROM operation";
    private static final String SELECT_BY_ID = "SELECT * FROM operation WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM operation WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO operation (operation_type, operation_cost, operation_time) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE operation SET operation_type=?, operation_cost=?, operation_time=? WHERE id=?";

    private final ConnectionManager connectionManager;
    private final SQLOperationParser operationParser;
    private final CRUDHandler<Operation> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager - connection manager
     * @param operationParser   - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicOperationDao(ConnectionManager connectionManager, SQLOperationParser operationParser) {

        this.connectionManager = connectionManager;
        this.operationParser = operationParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, operationParser);
    }

    /**
     * Selection all operations from data base
     *
     * @return list of the operations
     */
    @Override
    public List<Operation> findAll() throws DAOException {

        return crudHandler.findAll(SELECT_ALL);
    }

    /**
     * Selection operation by id
     *
     * @param operationId - operation id
     * @return operation from data base
     */
    @Override
    public Operation findById(Long operationId) throws DAOException {

        return crudHandler.findById(SELECT_BY_ID, operationId);
    }

    /**
     * Deletion operation from data base by id
     *
     * @param operationId - operation id
     * @return boolean result of the deletion from data base
     */
    @Override
    public boolean delete(Long operationId) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, operationId);
    }

    /**
     * Insertion operation to data base
     *
     * @param operation - operation
     * @return operation id from data base
     */
    @Override
    public Long create(Operation operation) throws DAOException {

        return crudHandler.create(INSERT_QUERY, operation);
    }

    /**
     * Updating operation in data base
     *
     * @param operation - operation
     * @return boolean result of the updating
     */
    @Override
    public boolean update(Operation operation) throws DAOException {

        return crudHandler.update(UPDATE_QUERY, operation);
    }
}
