package remote.runjetty.ui.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by max on 18.09.15.
 *
 * SSH login form page
 */
//TODO: i think this can be declared in the web.xml or another file
@WebServlet(
        description = "Login Servlet",
        urlPatterns = "/ui/Login"
)
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().write("Hallo");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String host = request.getParameter("host");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        System.out.println(host);
        System.out.println(user);
        System.out.println(password);
    }
}
