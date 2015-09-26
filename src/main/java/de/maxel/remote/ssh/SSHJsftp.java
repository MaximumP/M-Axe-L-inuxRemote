package de.maxel.remote.ssh;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import java.io.IOException;
import java.util.List;

/**
 * Created by max on 26.09.15.
 */
public class SSHJsftp {

    private SSHClient sshClient = null;
    private SFTPClient sftpClient = null;
    private String workingDirectory = "~/";

    public SSHJsftp(String host, String username, String password, String hostKey) {
        sshClient = new SSHClient();

        try {
            Session session = sshClient.startSession();
            Session.Command cmd = session.exec("pdw\n");
            workingDirectory = IOUtils.readFully(cmd.getInputStream()).toString();
            cmd.close();
            session.close();

            sshClient.addHostKeyVerifier(hostKey);
            sshClient.connect(host);
            sshClient.authPassword(username, password);
            sftpClient = sshClient.newSFTPClient();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RemoteResourceInfo> ls() {
        List<RemoteResourceInfo> dirContent = null;
        try {
            dirContent = sftpClient.ls(workingDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dirContent;
    }

    public boolean cd(String dir) {

        return false;
    }

    public boolean rm(String file) {

        return false;
    }

    public boolean rmDir(String dir) {

        return false;
    }
}
