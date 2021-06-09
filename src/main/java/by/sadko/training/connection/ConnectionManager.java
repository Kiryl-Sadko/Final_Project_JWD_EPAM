package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface describes behavior of the connection manager
 *
 * @author Sadko Kiryl
 * @version 1.0
 */
public interface ConnectionManager {

    Connection getConnection() throws SQLException, ConnectionException;
}
