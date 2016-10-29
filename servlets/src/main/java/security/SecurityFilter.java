package security;

import com.social_network.core.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebFilter(
        urlPatterns = {"/index.jsp", "/registration.jsp"}
)
public class SecurityFilter implements HttpFilter {
    private static String KEY = "key";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);

        if (session.getAttribute(KEY) != null)
            chain.doFilter(request, response);
        else request.getRequestDispatcher("/login.jsp").forward(request, response);


//        Map<String, String[]> parameterMap = request.getParameterMap();
//        if (parameterMap.containsKey("j_password") && parameterMap.containsKey("j_username")) {
//            // TODO: 22/10/2016
//
//            Optional<Person> authorize = authorize(parameterMap);
//            if (authorize.isPresent()) {
//                session.setAttribute(KEY, authorize.get());
//                chain.doFilter(request, response);
//            } else
//                request.getRequestDispatcher("/error.html").forward(request, response);
//
//        } else {
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.html");
//            // TODO: 22/10/2016 посмотреть что можно сделать что бы не терять информацию о странице куда пользователь зашёл
//            requestDispatcher.forward(request, response);
//        }
    }
}
