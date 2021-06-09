package by.sadko.training.dao.impl;

import by.sadko.training.connection.ConnectionManager;
import by.sadko.training.dao.CRUDHandler;
import by.sadko.training.dao.ProgressDao;
import by.sadko.training.entity.Progress;
import by.sadko.training.exception.DAOException;
import by.sadko.training.parser.SQLProgressParser;

import java.util.List;

/**
 * Class of the basic implementation ProgressDao
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ProgressDao,SQLProgressParser,CRUDHandler
 */
public class BasicProgressDao implements ProgressDao {

    private static final String SELECT_ALL = "SELECT * FROM progress";
    private static final String SELECT_BY_ID = "SELECT * FROM progress WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM progress WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO progress (progress_status, progress_log) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE progress SET progress_status=?, progress_log=? WHERE id=?";

    private final ConnectionManager connectionManager;
    private final SQLProgressParser progressParser;
    private final CRUDHandler<Progress> crudHandler;

    /**
     * Initialization of the command
     *
     * @param connectionManager - connection manager
     * @param progressParser    - parser from entity to sql-statement and from ResultSet to entity
     */
    public BasicProgressDao(ConnectionManager connectionManager, SQLProgressParser progressParser) {
        this.connectionManager = connectionManager;
        this.progressParser = progressParser;
        this.crudHandler = new CRUDHandler<>(connectionManager, progressParser);
    }

    /**
     * Selection progress list from data base
     *
     * @return list of the progress
     */
    @Override
    public List<Progress> findAll() throws DAOException {

        return crudHandler.findAll(SELECT_ALL);
    }

    /**
     * Selection progress from data base by id
     *
     * @param progressId - progress id
     * @return progress
     */
    @Override
    public Progress findById(Long progressId) throws DAOException {

        return crudHandler.findById(SELECT_BY_ID, progressId);
    }

    /**
     * Deletion progress from data base by id
     *
     * @param progressId - progress id
     * @return boolean result of the deletion
     */
    @Override
    public boolean delete(Long progressId) throws DAOException {

        return crudHandler.delete(DELETE_QUERY, progressId);
    }

    /**
     * Insertion progress to data base
     *
     * @param progress - progress
     * @return progress id from data base
     */
    @Override
    public Long create(Progress progress) throws DAOException {

        return crudHandler.create(INSERT_QUERY, progress);
    }

    /**
     * Updating progress in data base
     *
     * @param progress - progress
     * @return boolean result of the updating
     */
    @Override
    public boolean update(Progress progress) throws DAOException {

        return crudHandler.update(UPDATE_QUERY, progress);
    }
}
