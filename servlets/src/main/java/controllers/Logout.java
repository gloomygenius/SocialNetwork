package controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        if (session.getAttribute("key") != null) {
            session.removeAttribute("key");
        }
        try {
            response.sendRedirect("/index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 29.10.2016 log
        }
    }
}
