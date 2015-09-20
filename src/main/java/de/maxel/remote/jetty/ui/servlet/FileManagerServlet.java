package de.maxel.remote.jetty.ui.servlet;

import com.jcraft.jsch.JSchException;
import de.maxel.remote.ssh.SSHSession;
import de.maxel.remote.ssh.schell.commands.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import com.jcraft.jsch.Channel;
import java.util.List;

/**
 * Created by max on 20.09.15.
 */
public class FileManagerServlet extends HttpServlet {

    private static final String GET_DIRECTORY_COMMAND = "find . -maxdepth 1 -type d";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        //String workingDirectory = request.getParameter("working-directory");
        Command find = new Command(GET_DIRECTORY_COMMAND);
        Command pwd = new Command("pwd");
        System.out.println(pwd.executeCommand());
        String cdParam = request.getParameter("cd");
        String commandRes = find.executeCommand();
        List<String> directoryContent = getListFromString(commandRes);

        /*TODO: does not work. It seems that every command is executed in the home dir so the next dir list
        is the same*/
        /*if (cdParam != null && containsDirectory(cdParam, directoryContent)) {
            Command cd = new Command(cdParam);
            cd.executeCommand();
            System.out.println(pwd.executeCommand());
            directoryContent = getListFromString(find.executeCommand());
        }*/
        request.setAttribute("directories", directoryContent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ui/file-manager.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean containsDirectory(String directory, List<String> directories) {
        for (String value: directories) {
            if (directory.equals(value))
                return true;
        }
        return false;
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
