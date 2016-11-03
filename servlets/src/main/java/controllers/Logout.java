package controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static security.SecurityFilter.USER_KEY;

public class Logout extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    private void requestProcess(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(USER_KEY) != null) {
            session.removeAttribute(USER_KEY);
        }
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 29.10.2016 log
        }
    }
}
