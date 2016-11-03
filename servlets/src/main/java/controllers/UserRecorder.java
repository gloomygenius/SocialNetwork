package controllers;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.dao.h2.H2UserDao;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserRecorder extends HttpServlet {
    private static final Logger log = LogManager.getLogger(UserRecorder.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }
    @SneakyThrows
    private void requestProcess(HttpServletRequest request, HttpServletResponse response) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        UserDao userDao = new H2UserDao(connectionPool);
        request.setCharacterEncoding("UTF-8");
        String nextUrl = "/";
        String firstName = (String) request.getParameter("first_name");
        String lastName = (String) request.getParameter("last_name");
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("password");
        Boolean male = request.getParameter("gender").equals("male");
        //Boolean male = true;

        try {
            userDao.addNewUser(firstName, lastName, email, password, male);
            log.info("New user registered!");
        } catch (RuntimeException e) {
            log.error("Error when user registering", e);
            nextUrl = "/error.jsp";
        }
        try {
            response.sendRedirect(nextUrl);
        } catch (IOException e) {
            log.warn("Redirect error",e);
        }
    }
}