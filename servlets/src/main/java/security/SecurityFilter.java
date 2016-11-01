package security;

import com.social_network.core.HttpFilter;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static listeners.Initializer.USER_DAO;

public class SecurityFilter implements HttpFilter {
    public static String USER_KEY = "currentUser";
    private UserDao userDao;
    private static final Logger log = LogManager.getLogger(SecurityFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(USER_KEY) != null)
            chain.doFilter(request, response);
        else {
            log.info("Notauth Пришёл неавторизованный пользвоатель");
            log.info("check Проверяем, логинится ли он...");
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.containsKey("j_password") && parameterMap.containsKey("j_username")) {
                log.info("LogPasCheck да, он пришёл с логином и паролем, сейчас проверим...");
                Optional<User> authorize = authorize(parameterMap);
                if (authorize.isPresent()) {
                    session.setAttribute(USER_KEY, authorize.get());
                    response.sendRedirect("/");
                    log.info("Yes Да такой пользователь есть");
                } else{
                    log.info("No пользователь нет");
                    request.getRequestDispatcher("/auth/error.jsp").forward(request, response);
                }
            } else {
                log.info("Redir чел просто зашёл, отправляем логиниться");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/auth/login.jsp");
                // TODO: 22/10/2016 посмотреть что можно сделать что бы не терять информацию о странице куда пользователь зашёл
                requestDispatcher.forward(request, response);
            }
        }
    }
    private Optional<User> authorize(Map<String, String[]> parameterMap) {
        String login = parameterMap.get("j_username")[0];
        String password = parameterMap.get("j_password")[0];
        return userDao.isUserRegistered(login, password);
    }
}