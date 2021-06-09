package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;

import java.sql.Connection;

/**
 * Class of the basic implementation of the command manager
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ConnectionManager,ConnectionPool,TransactionManager
 */
public class BasicConnectionManager implements ConnectionManager {

    private final ConnectionPool<Connection> connectionPool;
    private final TransactionManager transactionManager;

    /**
     * Initializing basic connection manager
     *
     * @param connectionPool     - pool of the jdbc connections
     * @param transactionManager - manager of the transaction statements
     */
    public BasicConnectionManager(ConnectionPool<Connection> connectionPool, TransactionManager transactionManager) {

        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }

    /**
     * Returns jdbc connection from connection pool(if statement isn't transactional) or from transaction manager
     * (if statement is transactional)
     *
     * @return sql connection
     */
    @Override
    public Connection getConnection() throws ConnectionException {

        if (transactionManager.isEmpty()) {
            return connectionPool.getConnection();

        } else {
            return transactionManager.getConnection();
        }
    }
}
