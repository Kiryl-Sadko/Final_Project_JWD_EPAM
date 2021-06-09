package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.sadko.training.util.AppConstants.*;

/**
 * Class of the basic implementation of the connection pool.
 * Should be a singleton.
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ConnectionPool,Connection,
 */
public class BasicConnectionPool implements ConnectionPool<Connection> {

    private static final Logger LOGGER = LogManager.getLogger(BasicConnectionPool.class);

    private static final AtomicBoolean IS_INSTANCE_EXIST = new AtomicBoolean(false);
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static BasicConnectionPool instance;
    private final Lock connectionLock = new ReentrantLock();
    private final Condition emptyPool = connectionLock.newCondition();

    private final String url;
    private final String user;
    private final String password;
    private final int poolCapacity;

    private final LinkedList<Connection> availableConnections = new LinkedList<>();
    private final LinkedList<Connection> usedConnections = new LinkedList<>();

    /**
     * Initializing of the connection pool
     */
    private BasicConnectionPool() {

        try (InputStream resourceAsStream = BasicConnectionPool.class
                .getClassLoader()
                .getResourceAsStream(DATASOURCE_PROPERTIES_PATH)) {

            Properties prop = new Properties();
            prop.load(resourceAsStream);

            String driver = prop.getProperty(DATASOURCE_DRIVER);
            initDriver(driver);

            this.url = prop.getProperty(DATASOURCE_URL);
            this.user = prop.getProperty(DATASOURCE_USER);
            this.password = prop.getProperty(DATASOURCE_PASSWORD);
            this.poolCapacity = Integer.parseInt(prop.getProperty(DATASOURCE_POOL_CAPACITY));

        } catch (IOException ioException) {
            LOGGER.error("Properties file not found");
            throw new IllegalStateException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Executes default constructor and returns it, if instance is empty, or return instance, if it is existed
     *
     * @return instance of the connection pool
     */
    public static BasicConnectionPool getInstance() {

        if (!IS_INSTANCE_EXIST.get()) {
            INSTANCE_LOCK.lock();
            try {
                if (instance == null) {
                    instance = new BasicConnectionPool();
                    IS_INSTANCE_EXIST.set(true);
                }

            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return instance;
    }

    /**
     * Initializing jdbc driver
     *
     * @param driver - jdbc driver
     */
    private void initDriver(String driver) {

        try {
            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            LOGGER.error("Driver cannot be found");
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Returns connection from connection pool, if it available, or waits until connection would be available
     *
     * @return sql connection to data base
     */
    @Override
    public Connection getConnection() throws ConnectionException {

        connectionLock.lock();
        Connection connectionProxy = null;
        try {
            if (availableConnections.isEmpty() && usedConnections.size() == poolCapacity) {
                emptyPool.await();
            }

            //lazy getting connection
            if (availableConnections.isEmpty() && usedConnections.size() < poolCapacity) {
                Connection connection = DriverManager.getConnection(url, user, password);
                availableConnections.add(connection);

            } else if (availableConnections.isEmpty()) {
                LOGGER.error("Get Maximum pool size was reached");
                throw new ConnectionException("Get Maximum pool size was reached");
            }

            Connection connection = availableConnections.removeFirst();
            usedConnections.add(connection);
            connectionProxy = createProxyConnection(connection);

        } catch (SQLException | InterruptedException exception) {
            LOGGER.error("A connection can't be created");
            throw new ConnectionException(exception.getMessage(), exception);

        } finally {
            connectionLock.unlock();
        }

        return connectionProxy;
    }

    /**
     * Returns proxy of the sql connection. Proxy should change method 'close' to release connection and return it in
     * connection pool
     *
     * @param connection - sql connection
     * @return proxy of the sql connection
     */
    private Connection createProxyConnection(Connection connection) {

        return (Connection) Proxy.newProxyInstance(connection.getClass()
                .getClassLoader(), new Class[]{Connection.class}, (proxy, method, args) -> {

            if ("close".equals(method.getName())) {
                releaseConnection(connection);
                return null;

            } else if ("hashCode".equals(method.getName())) {
                return connection.hashCode();

            } else {
                return method.invoke(connection, args);
            }
        });
    }

    /**
     * Releases connection from container with used connections and returns it to container with available connections
     *
     * @param connection - sql connection
     */
    @Override
    public void releaseConnection(Connection connection) throws ConnectionException {

        connectionLock.lock();
        try {
            usedConnections.remove(connection);
            availableConnections.add(connection);
            emptyPool.signal();

        } catch (Exception e) {
            LOGGER.error(e);
            throw new ConnectionException(e.getMessage(), e);

        } finally {
            connectionLock.unlock();
        }
    }

    /**
     * Closes all connections and clears containers of the connection pool
     */
    @Override
    public void shutdown() {

        connectionLock.lock();
        try {
            if (!availableConnections.isEmpty() || !usedConnections.isEmpty()) {

                usedConnections.forEach(connection -> {
                    try {
                        connection.close();
                    } catch (SQLException sqlException) {
                        LOGGER.error("A connection can't be closed");
                    }
                });

                usedConnections.clear();

                availableConnections.forEach(connection -> {
                    try {
                        connection.close();
                    } catch (SQLException sqlException) {
                        LOGGER.error("A connection can't be closed");
                    }
                });

                availableConnections.clear();
            }

        } finally {
            connectionLock.unlock();
        }
    }

    /**
     * Returns url of the data base
     *
     * @return url of the data base
     */
    @Override
    public String getUrl() {

        return url;
    }

    /**
     * Returns user of the data base
     *
     * @return user of the data base
     */
    @Override
    public String getUser() {

        return user;
    }

    /**
     * Returns size of the connection pool
     *
     * @return size of the connection pool
     */
    @Override
    public int getPoolSize() {

        AtomicInteger poolSize = new AtomicInteger(availableConnections.size()
                + usedConnections.size());
        return poolSize.get();
    }
}
