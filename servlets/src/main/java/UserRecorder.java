import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserRecorder extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    private void requestProcess(HttpServletRequest request, HttpServletResponse response) {
        String login = (String) request.getAttribute("login");
        String password = (String) request.getAttribute("password");

        PrintWriter out= null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Твой логин: "+login);
        out.println("Твой пароль: "+password);
    }
}