package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;

import java.sql.Connection;

/**
 * Interface describes behavior of the transaction manager
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface TransactionManager {

    Connection getConnection();

    void begin() throws ConnectionException;

    void commit() throws ConnectionException;

    void rollback() throws ConnectionException;

    boolean isEmpty();
}
