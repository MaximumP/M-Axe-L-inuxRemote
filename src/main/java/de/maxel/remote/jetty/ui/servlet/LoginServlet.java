package de.maxel.remote.jetty.ui.servlet;

import de.maxel.remote.ssh.SshClient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by max on 18.09.15.
 *
 * SSH login form page
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_OK);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ui/Login.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("user");
        String host = request.getParameter("host");
        String password = request.getParameter("password");

        //TODO: ask user if he wants to accept the host key
        SshClient.getInstance().connect(host, userName, password,
                "e8:1d:fd:df:09:5f:c5:7c:ea:47:a6:51:09:98:87:02");
        System.out.println("Session connected");
        try {
            response.sendRedirect("/maxel/FileManager");
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
