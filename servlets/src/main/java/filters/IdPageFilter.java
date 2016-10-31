package filters;

import com.social_network.core.HttpFilter;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import security.SecurityFilter;

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


public class IdPageFilter implements HttpFilter {
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
        if (matcher.find()) {
            log.info("id was found, put into session");
            int id = Integer.parseInt(matcher.group(1));
            HttpSession session = request.getSession(true);
            Optional<User> user = userDao.getById(id);
            if (user.isPresent())
                session.setAttribute(REF_PAGE, user.get());

            else session.setAttribute(REF_PAGE,new User(0,"na","na","na","na",true));
            log.info("forward to /page");
            request.getRequestDispatcher("/pages/personal_page.jsp").forward(request, response);
        }else
        chain.doFilter(request, response);
    }
}