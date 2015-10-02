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
    private List<RemoteResourceInfo> currentDirConenten = null;
    private static final String DIR_SEPARATOR = "/";

    public SSHJsftp(SSHClient client) throws IOException {
        sshClient = client;
        sftpClient = sshClient.newSFTPClient();
        Session session = sshClient.startSession();
        Session.Command cmd = session.exec("pwd\n");
        workingDirectory = IOUtils.readFully(cmd.getInputStream()).toString();
        System.out.println(workingDirectory);
        cmd.close();
        session.close();
    }

    public List<RemoteResourceInfo> ls() {
        List<RemoteResourceInfo> dirContent = null;
        try {
            dirContent = sftpClient.ls(workingDirectory.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dirContent;
    }

    public void cd(String dir) {
        // TODO: check if the directory exists
        boolean endsWithSlash = workingDirectory.endsWith(DIR_SEPARATOR);
        if (dir.startsWith("/")) {
            workingDirectory = dir;
        } else {
            if (dir.equals("..")) {
                // already the root path. Do nothing
                if (workingDirectory.equals(DIR_SEPARATOR))
                    return;
                else {
                    //move directory up
                    workingDirectory = endsWithSlash ?
                            workingDirectory.substring(0, workingDirectory.length() -1) :
                            workingDirectory;
                    int i = workingDirectory.indexOf("/");
                    workingDirectory = workingDirectory.substring(0, i);
                }
            }
            else {
                // append path to working directory
                workingDirectory = endsWithSlash ?
                    workingDirectory.trim() + dir :
                    workingDirectory.trim() + DIR_SEPARATOR + dir;
            }
        }

        System.out.println(workingDirectory);
    }

    public boolean rm(String file) {

        return false;
    }

    public boolean rmDir(String dir) {

        return false;
    }
}
