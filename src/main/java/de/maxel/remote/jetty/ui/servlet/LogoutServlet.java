package de.maxel.remote.jetty.ui.servlet;

import de.maxel.remote.ssh.SSHSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by max on 20.09.15.
 *
 * Closes the ssh session
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        SSHSession session = SSHSession.getInstance();
        session.endSession();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ui/Login.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
