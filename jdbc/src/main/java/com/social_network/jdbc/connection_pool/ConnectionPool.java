package com.social_network.jdbc.connection_pool;


import lombok.Getter;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("WeakerAccess")
public class ConnectionPool {
    @Getter
    private static ConnectionPool instance;
    @Getter
    private BlockingQueue<Connection> connectionQueue;
    @Getter
    private BlockingQueue<Connection> givenAwayConQueue;
    String driverName;
    String url;
    String user;
    String password;
    int poolSize;

    private ConnectionPool(String pathToParams) {

        try (FileInputStream fileInputStream = new FileInputStream(pathToParams);
             BufferedInputStream stream = new BufferedInputStream(fileInputStream)) {

            Properties dbProperties = new Properties();
            dbProperties.load(stream);
            this.driverName = dbProperties.getProperty("driver");
            this.url = dbProperties.getProperty("url");
            this.user = dbProperties.getProperty("user");
            this.password = dbProperties.getProperty("password");
            this.poolSize = Integer.parseInt(dbProperties.getProperty("poolSize"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create(String pathToParams) {
        instance = new ConnectionPool(pathToParams);
    }

    public void initPoolData() throws ConnectionPoolException {
        System.setProperty("file.encoding","UTF-8");
        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in connection_pool", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(
                    "Can't find database driver class", e);
        }
    }

    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(
                    "Error connecting to the data source.", e);
        }
        return connection;
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue)
            throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    public void initSqlData(String pathToInitSQL) throws ConnectionPoolException {
        File file = new File(pathToInitSQL);
        char[] buf = new char[(int) file.length()];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            //noinspection ResultOfMethodCallIgnored
            reader.read(buf);
        } catch (IOException e) {
            throw new ConnectionPoolException("Init SQL script error", e);
        }
        String[] initState = new String(buf).split(";");
        // TODO: 29.10.2016 отрефакторить
        Connection connection = instance.takeConnection();
        try (Statement statement = connection.createStatement()) {
            for (String state : initState) {
                statement.addBatch(state);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new ConnectionPoolException("Init SQL script error", e);
        }
    }
}