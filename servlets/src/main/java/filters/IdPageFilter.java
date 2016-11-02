package filters;

import com.social_network.core.HttpFilter;
import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.dao.UserInfoDao;
import com.social_network.jdbc.dao.h2.H2UserInfo;
import com.social_network.jdbc.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static listeners.Initializer.USER_DAO;
import static security.SecurityFilter.USER_KEY;


public class IdPageFilter implements HttpFilter {
    private static final String USER_INFO = "userInfo";
    private UserDao userDao;
    private Pattern pattern;
    public final static String REF_PAGE = "referencePage";
    private static final Logger log = LogManager.getLogger(IdPageFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
        pattern = Pattern.compile("\\/id([\\d]+)$");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("start id filter");
        Matcher matcher = pattern.matcher(request.getRequestURL());
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(USER_KEY);
        if (matcher.find()) {
            if (user == null) request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            else {
                log.info("ID in url was found, put into session");
                int id = Integer.parseInt(matcher.group(1));
                Optional<User> refUser = userDao.getById(id);
                if (refUser.isPresent()) {
                    session.setAttribute(REF_PAGE, refUser.get());
                    putInfoAboutUser(session, refUser.get().getId());
                } else {
                    session.setAttribute(REF_PAGE, new User(0, "na", "na", "na", "na", true));
                }
                log.info("forward to personal page");
                request.getRequestDispatcher("/jsp/personal_page.jsp").forward(request, response);
            }
        } else
            chain.doFilter(request, response);
    }

    private void putInfoAboutUser(HttpSession session, int id) {
        UserInfoDao userInfoDao = new H2UserInfo(ConnectionPool.getInstance());
        session.setAttribute(USER_INFO, userInfoDao.getById(id));
    }
}