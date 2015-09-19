package de.maxel.remote.jetty.ui.servlet;

import de.maxel.remote.ssh.SSHSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ui/Login.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String host = request.getParameter("host");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        SSHSession session = SSHSession.getInstance(host, user, password);
        session.startSession();
    }
}
