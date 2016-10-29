package listeners;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {
    public static final String PERSON_DAO = "personDao";
    public static final String GUN_DAO = "gunDao";
    public static final String INSTANCE_DAO = "instanceDao";
    private static Logger log = LoggerFactory.getLogger(Initializer.class);

    @Override

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ConnectionPool connectionPool;
        String pathToDbConfig = context.getRealPath("/") + "WEB-INF/classes/";
        ConnectionPool.create(pathToDbConfig + "db.properties");
        connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            log.error("Connection pool initialization error ", e.getMessage());
        }
        System.out.println("Инициализация!!!!!!!!!!!!!!!!!!");
        log.error("Connection pool successfully initialized");
        // TODO: 29.10.2016 проинициализировать саму базу
//        context.setAttribute(PERSON_DAO, new H2PersonDao(connectionPool));
//        context.setAttribute(GUN_DAO, new H2GunDao(connectionPool));
//        context.setAttribute(INSTANCE_DAO, new H2InstanceDao(connectionPool));
    }
}