package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;

/**
 * Interface describes behavior of the connection pool
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface ConnectionPool<T> {

    T getConnection() throws ConnectionException;

    void releaseConnection(T connection) throws ConnectionException;

    void shutdown();

    String getUrl();

    String getUser();

    int getPoolSize();
}
