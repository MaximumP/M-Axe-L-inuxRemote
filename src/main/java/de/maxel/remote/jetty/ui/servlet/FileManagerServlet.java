package de.maxel.remote.jetty.ui.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import de.maxel.remote.Maxel;
import de.maxel.remote.ssh.schell.commands.Shell;

import java.util.List;

/**
 * Created by max on 20.09.15.
 *
 * Servlet to browse the remote filesystem
 */
public class FileManagerServlet extends HttpServlet {

    private static final String GET_DIRECTORY_COMMAND = "find . -maxdepth 1 -type d";
    Shell shell = new Shell("max", "localhost", "La24!mmae");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String cdParam = request.getParameter("cd");

        if (cdParam != null) {
            shell.write("cd " + cdParam);
        }
        String commandRes = shell.write(GET_DIRECTORY_COMMAND);

        List<String> directoryContent = getListFromString(commandRes);
        request.setAttribute("directories", directoryContent);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/ui/file-manager.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getListFromString(String commandRes) {
        String[] files = commandRes.split("./");
        List<String> result = new ArrayList<>();
        for (int i = 0; i < files.length -1; i++) {
            result.add(files[i].trim());
        }
        return result;
    }
}
