package de.maxel.remote.ssh;

import net.schmizz.sshj.SSHClient;
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

    public SSHJsftp(String host, String username, String password, String hostKey) {
        sshClient = new SSHClient();
        try {
            sshClient.addHostKeyVerifier(hostKey);
            sshClient.connect(host);
            sshClient.authPassword(username, password);
            sftpClient = sshClient.newSFTPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printDirContent(String path) {
        try {
            List<RemoteResourceInfo> res = sftpClient.ls(path);
            for (RemoteResourceInfo info : res) {
                System.out.println(info.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
