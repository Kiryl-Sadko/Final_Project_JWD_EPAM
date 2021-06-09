package by.sadko.training.dao;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.entity.Entity;
import by.sadko.training.exception.ConnectionException;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLEntityParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Class-handler of the CRUD operations
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ConnectionManager,Entity,SQLEntityParser,
 */
public class CRUDHandler<T extends Entity> {

    private static final Logger LOGGER = LogManager.getLogger(CRUDHandler.class);

    private final ConnectionManager connectionManager;
    private final SQLEntityParser<T> entityParser;

    /**
     * Initializing of the class
     *
     * @param connectionManager - connection manager
     * @param entityParser      - entity parser
     */
    public CRUDHandler(ConnectionManager connectionManager, SQLEntityParser<T> entityParser) {

        this.connectionManager = connectionManager;
        this.entityParser = entityParser;
    }

    /**
     * Searching all entity from data base by sql-query
     *
     * @param query - sql-query
     * @return List of the entities
     */
    public List<T> findAll(String query) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return entityParser.parseResultSetToEntityList(resultSet);

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during selection all rows");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Selection entities by id from data base
     *
     * @param query - sql-query
     * @param id    - entities id
     * @return entity
     */
    public T findById(String query, Long id) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> entityList = entityParser.parseResultSetToEntityList(resultSet);

            T entity;
            if (!entityList.isEmpty() && (entity = entityList.get(0)) != null) {
                return entity;

            } else {
                LOGGER.info("The entity with ID={} is not founded", id);
                return null;
            }

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during selection by ID");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Insertion entity to data base
     *
     * @param query  - sql-query
     * @param entity - entity
     * @return entity id
     */
    public Long create(String query, T entity) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            PreparedStatement preparedStatement = entityParser.parseEntityToStatement(entity, statement);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            long id = generatedKeys.getLong(1);
            entity.setId(id);
            return id;

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during insertion");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Removing entity from data base
     *
     * @param query  - sql-query
     * @param entity - entity for removing
     * @return boolean result of the removing
     */
    public boolean delete(String query, Entity entity) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, entity.getId());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during removing");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Removing entity from data base
     *
     * @param query - sql-query
     * @param id    - entity's id for removing
     * @return boolean result of the removing
     */
    public boolean delete(String query, Long id) throws DAOException {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException | ConnectionException exception) {
            LOGGER.error("An exception is occurred during removing");
            throw new DAOException(exception.getMessage(), exception);
        }
    }

    /**
     * Updating entity in data base
     *
     * @param query  - sql-query
     * @param entity - entity
     * @return boolean result of the updating
     */
    public boolean update(String query, T entity) {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            PreparedStatement preparedStatement = entityParser.parseEntityToStatement(entity, statement);
            int parameterQuantity = preparedStatement.getParameterMetaData().getParameterCount();
            preparedStatement.setLong(parameterQuantity, entity.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException | ConnectionException exception) {
            LOGGER.info("An exception is occurred during updating");

            return false;
        }
    }
}
