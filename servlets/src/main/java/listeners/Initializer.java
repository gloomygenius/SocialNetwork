package listeners;

import com.social_network.jdbc.SQLscriptExecuter;
import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.dao.h2.H2UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {
    public static final String USER_DAO = "userDao";

    private static final Logger log = LogManager.getLogger(Initializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ConnectionPool connectionPool;
        String pathToDbConfig = context.getRealPath("/") + "WEB-INF/classes/";
        ConnectionPool.create(pathToDbConfig + "db.properties");
        connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
            log.info("Connection pool successfully initialized");
        } catch (ConnectionPoolException e) {
            log.error("Connection pool initialization error ", e);
        }

        try {
            SQLscriptExecuter.initSqlData(pathToDbConfig + "H2Init.sql");
            log.info("SQL initialization has done successfully");
        } catch (ConnectionPoolException e) {
            log.error("Error in SQL initialization", e);
        }

        context.setAttribute(USER_DAO, new H2UserDao(connectionPool));
    }
}