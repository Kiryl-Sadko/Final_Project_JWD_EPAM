package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class BasicConnectionPoolTest {

    private static ConnectionPool<Connection> connectionPool;

    @BeforeClass
    public static void setUp() {
        connectionPool = BasicConnectionPool.getInstance();
    }

    @AfterClass
    public static void shutDown() {
        connectionPool.shutdown();
    }

    @Test
    public void shouldReturnConnection() throws SQLException, ConnectionException {

        Connection connection = connectionPool.getConnection();
        Assert.assertNotNull(connection);
        connection.close();
    }

    @Test
    public void closingConnectionShouldNotRemoveItFromPool() throws ConnectionException, SQLException {

        Connection connection = connectionPool.getConnection();
        assert connection != null;
        connection.close();

        Assert.assertEquals(1, connectionPool.getPoolSize());
    }

    @Test
    public void shutdownShouldClearPool() throws ConnectionException {

        Connection connection = connectionPool.getConnection();
        connectionPool.shutdown();

        Assert.assertEquals(0, connectionPool.getPoolSize());
    }

    @Test
    public void poolShouldBeSingleton() {

        ConnectionPool<Connection> secondPool = BasicConnectionPool.getInstance();
        Assert.assertEquals(connectionPool, secondPool);
    }
}
