package com.madikhan.estore.jdbc;

import com.madikhan.estore.jdbc.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.madikhan.estore.constants.DBParameterConstants.*;
import static com.madikhan.estore.constants.NamesConstants.*;

public class ConnectionPool {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static volatile ConnectionPool instance;
    private static final Integer DEFAULT_POOL_SIZE = 10;

    private PropertiesUtil properties = PropertiesUtil.getInstance();
    private BlockingQueue<Connection> poolQueue;
    private String driverName;
    private String url;
    private String username;
    private String password;
    private int poolSize;

    private ConnectionPool() {
        init();
    }

    private void init() {
        setParameters();
        loadDriver();
        initConnectionPool();
    }

    private void setParameters() {
        driverName = properties.getValue(DRIVER_NAME_KEY);
        url = properties.getValue(URL_KEY);
        username = properties.getValue(USERNAME_KEY);
        password = properties.getValue(PASSWORD_KEY);
        try {
            poolSize = Integer.parseInt(properties.getValue(POOL_SIZE_KEY));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            poolSize = DEFAULT_POOL_SIZE;
            LOGGER.warn(ERROR_POOL_SIZE_PROPERTIES + e.getMessage());
        }
        poolQueue = new ArrayBlockingQueue<>(poolSize);
    }

    private void loadDriver() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.error(ERROR_FIND_JDBC_DRIVER_CLASS + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void initConnectionPool() {

        while (poolQueue.size() < poolSize) {
            try {
                poolQueue.put(openConnection());
            } catch (InterruptedException e) {
                e.printStackTrace();
                LOGGER.error(ERROR_THREAD_INTERRUPTED_WHILE_INIT_CONNECTION + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private Connection openConnection() {
        try {
            return DriverManager.getConnection(
                    url,
                    username,
                    password
            );
        } catch (SQLException e) {
            LOGGER.error(ERROR_CANNOT_OPEN_CONNECTION + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /*
    Singleton Using Volatile Bean Pattern
    */
    public static ConnectionPool getInstance() {
        ConnectionPool tempInstance = instance;
        if (tempInstance == null) {
            synchronized (ConnectionPool.class) {    // While we were waiting for the connection pool, another
                tempInstance = instance;        // thread may have instantiated the object.
                if (tempInstance == null) {
                    tempInstance = new ConnectionPool();
                    instance = tempInstance;
                }
            }
        }
        return tempInstance;
    }

    public synchronized Connection getConnection() {
        try {
            return poolQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error(ERROR_THREAD_INTERRUPTED_WHILE_GET_CONNECTION + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public synchronized void bringBackConnection(Connection connection) {
        if ((poolQueue.size() < poolSize) && (connection != null)) {
            try {
                poolQueue.put(connection);
            } catch (InterruptedException e) {
                LOGGER.error(ERROR_THREAD_INTERRUPTED_WHILE_BRING_BACK_CONNECTION + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
