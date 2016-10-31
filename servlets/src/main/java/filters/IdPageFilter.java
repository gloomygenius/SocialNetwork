package filters;

import com.social_network.core.HttpFilter;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.model.User;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static listeners.Initializer.USER_DAO;

//@WebFilter("/id*")
public class IdPageFilter implements HttpFilter {
    private UserDao userDao;
    private Pattern pattern;
    private Matcher matcher;
    private String REF_PAGE = "referencePage";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);

        pattern = Pattern.compile("\\/id[^0]([\\d]+)$");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        matcher = pattern.matcher(request.getRequestURL());
        if (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            HttpSession session = request.getSession(true);
            Optional<User> user = userDao.getById(id);
            if (user.isPresent())
                session.setAttribute(REF_PAGE, user.get());
            request.getRequestDispatcher("/").forward(request,response);
        }
        chain.doFilter(request, response);
    }
}