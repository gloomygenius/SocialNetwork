package security;

import com.social_network.core.HttpFilter;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebFilter(
        urlPatterns = {"/*"}
)
public class SecurityFilter implements HttpFilter {
    private static String KEY = "key";
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userDao = (UserDao) servletContext.getAttribute("userDao");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);

        if (session.getAttribute(KEY) != null)
            chain.doFilter(request, response);
        else request.getRequestDispatcher("/login.jsp").forward(request, response);

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("j_password") && parameterMap.containsKey("j_username")) {
            // TODO: 22/10/2016

            Optional<User> authorize = authorize(parameterMap);
            if (authorize.isPresent()) {
                session.setAttribute(KEY, authorize.get());
                response.sendRedirect("/");
                chain.doFilter(request, response);
            } else
                request.getRequestDispatcher("/error.jsp").forward(request, response);

        }
//        else {
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.html");
//            // TODO: 22/10/2016 посмотреть что можно сделать что бы не терять информацию о странице куда пользователь зашёл
//            requestDispatcher.forward(request, response);
//        }

    }

    private Optional<User> authorize(Map<String, String[]> parameterMap) {
        String login = parameterMap.get("j_username")[0];
        String password = parameterMap.get("j_password")[0];

        return userDao.isUserRegistered(login, password);
    }
}