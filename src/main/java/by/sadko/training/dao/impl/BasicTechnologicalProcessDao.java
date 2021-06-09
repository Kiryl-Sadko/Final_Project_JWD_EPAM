package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.TechnologicalProcessDao;
import by.sadko.training.entity.Operation;
import by.sadko.training.entity.TechnologicalProcess;
import by.sadko.training.exception.ConnectionException;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLOperationParser;
import by.sadko.training.parser.SQLTechnologicalProcessParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Class of the basic implementation TechnologicalProcessDao
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see TechnologicalProcessDao,SQLTechnologicalProcessParser,CRUDHandler
 */
public class BasicTechnologicalProcessDao implements TechnologicalProcessDao {

    private static final Logger LOGGER = LogManager.getLogger(BasicTechnologicalProcessDao.class);

    private static final String SELECT_ALL_QUERY = "SELECT * FROM technological_process";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM technological_process WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO technological_process " +
            "(technological_process_name) VALUES (?)";
    private static final String DELETE_QUERY = "DELETE FROM technological_process WHERE id=?";
    private static final String UPDATE_QUERY = "UPDATE technological_process SET technological_process_name=?, WHERE id=?";
    private static final String SELECT_OPERATIONS = "SELECT operation.id, operation.operation_type, " +
            "operation.operation_cost, operation.operation_time FROM operation JOIN operations_order " +
            "ON operations_order.technological_process_id=? WHERE operation_id=operation.id";
    private static final String INSERT_OPERATION_QUERY = "INSERT INTO operations_order " +
            "(technological_process_id, operation_id) VALUES(?,?)";

    private final ConnectionManager connectionManager;
    private final SQLTechnologicalProcessParser technologicalProcessParser;
    private final CRUDHandler<TechnologicalProcess> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager          - connection manager
     * @param technologicalProcessParser - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicTechnologicalProcessDao(ConnectionManager connectionManager,
                                        SQLTechnologicalProcessParser technologicalProcessParser) {
        this.connectionManager = connectionManager;
        this.technologicalProcessParser = technologicalProcessParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, technologicalProcessParser);
    }

    /**
     * Selection technological process from data base by operation list
     *
     * @param operationList - operation list
     * @return technological process id
     */
    @Override
    public Long findByOperationList(List<Operation> operationList) throws DAOException {

        List<TechnologicalProcess> all = findAll();
        for (TechnologicalProcess technologicalProcess : all) {
            List<Operation> operationQueue = selectOperationQueue(technologicalProcess.getId());

            if (operationList.equals(operationQueue)) {
                return technologicalProcess.getId();
            }
        }
        return null;
    }

    /**
     * Selection technological process list
     *
     * @return technological process list
     */
    @Override
    public List<TechnologicalProcess> findAll() throws DAOException {

        List<TechnologicalProcess> all = crudHandler.findAll(SELECT_ALL_QUERY);
        for (TechnologicalProcess tp : all) {
            setOperationsQueue(tp);
        }
        return all;
    }

    /**
     * Selection technological process from data base by id
     *
     * @param technologicalProcessId - technological process id
     * @return technological process
     */
    @Override
    public TechnologicalProcess findById(Long technologicalProcessId) throws DAOException {

        Optional<TechnologicalProcess> optionalById = Optional.ofNullable(crudHandler.findById(SELECT_BY_ID_QUERY, technologicalProcessId));
        if (optionalById.isPresent()) {
            setOperationsQueue(optionalById.get());
        }
        return optionalById.orElse(null);
    }

    /**
     * Deletion technological process from data base
     *
     * @param technologicalProcessId - - technological process id
     * @return boolean result of the deletion
     */
    @Override
    public boolean delete(Long technologicalProcessId) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, technologicalProcessId);
    }

    /**
     * Insertion technological process to data base
     *
     * @param technologicalProcess - technological process
     * @return technological process id from data base
     */
    @Override
    public Long create(TechnologicalProcess technologicalProcess) throws DAOException {

        Long technologicalProcessId = crudHandler.create(INSERT_QUERY, technologicalProcess);
        List<Operation> operationQueue = technologicalProcess.getOperationQueue();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OPERATION_QUERY)) {

            for (Operation operation : operationQueue) {
                preparedStatement.setLong(1, technologicalProcessId);
                preparedStatement.setLong(2, operation.getId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during technological process creating");
            throw new DAOException(exception.getMessage(), exception);
        }
        return technologicalProcessId;
    }

    /**
     * Updating technological process in data base
     *
     * @param technologicalProcess - technological process
     * @return boolean result of the updating
     */
    @Override
    public boolean update(TechnologicalProcess technologicalProcess) throws DAOException {

        return crudHandler.update(UPDATE_QUERY, technologicalProcess);
    }

    /**
     * Selection operation queue of the technological process from data base
     *
     * @param technologicalProcessId - technological process id
     * @return operation list
     */
    @Override
    public List<Operation> selectOperationQueue(Long technologicalProcessId) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OPERATIONS)) {

            preparedStatement.setLong(1, technologicalProcessId);
            ResultSet resultSet = preparedStatement.executeQuery();
            SQLOperationParser operationParser = new SQLOperationParser();
            return operationParser.parseResultSetToEntityList(resultSet);

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during operations queue's selection");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Setting operation queue to technological process
     *
     * @param technologicalProcess - technological process
     */
    private void setOperationsQueue(TechnologicalProcess technologicalProcess) throws DAOException {

        List<Operation> operationQueue = selectOperationQueue(technologicalProcess.getId());
        technologicalProcess.setOperationQueue(operationQueue);
    }
}
