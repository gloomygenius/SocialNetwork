package com.social_network.jdbc;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLscriptExecuter {
    private static Logger log = LogManager.getLogger(SQLscriptExecuter.class);

    public static void initSqlData(String pathToInitSQL) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        File file = new File(pathToInitSQL);
        char[] buf = new char[(int) file.length()];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            //noinspection ResultOfMethodCallIgnored
            reader.read(buf);
        } catch (IOException e) {
            log.fatal("Reading sql script file error", e);
        }
        String[] initState = new String(buf).split(";");
        // TODO: 29.10.2016 отрефакторить

        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement()) {
            for (String state : initState) {
                statement.executeUpdate(state);
            }
        } catch (SQLException e) {
            log.fatal("Init SQL script error", e);
        } catch (ConnectionPoolException e) {
            log.fatal("Error in ConnectionPool when sql script was executed", e);
        }
    }
}
