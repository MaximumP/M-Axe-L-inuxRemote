package de.maxel.remote.jetty.ui.servlet;

import de.maxel.remote.ssh.SSHJsftp;
import de.maxel.remote.ssh.SshClient;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

/**
 * Created by max on 20.09.15.
 *
 * Servlet to browse the remote filesystem
 */
public class FileManagerServlet extends HttpServlet {

    private SSHJsftp sftpClient = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            if (sftpClient == null) {
                SSHClient client = SshClient.getInstance().getSshClient();
                sftpClient = new SSHJsftp(client);
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        String cdParam = request.getParameter("cd");
        if (cdParam != null) {
            sftpClient.cd(cdParam);
        }

        List<RemoteResourceInfo> dirContent = sftpClient.ls();
        request.setAttribute("directories", dirContent);
        /*for (RemoteResourceInfo info : dirContent)
        {
            System.out.println(info.toString());
        }*/

        RequestDispatcher dispatcher = request.getRequestDispatcher("/ui/file-manager.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
