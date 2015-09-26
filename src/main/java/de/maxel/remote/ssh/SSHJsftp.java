package de.maxel.remote.ssh;


import net.schmizz.concurrent.Event;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.StreamCopier;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by max on 26.09.15.
 */
public class SSHJsftp {

    private SSHClient sshClient = null;
    private SFTPClient sftpClient = null;
    private Session session  = null;

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

    public Session.Command execCmd(String cmd){
        //TODO: throw exceptions
        try {

            session= this.sshClient.startSession();
            return session.exec(cmd);
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Session.Shell shell;
    public void createTerminal() throws IOException {
        //sshClient.authPublickey(System.getProperty("user.name"));

        final Session session = sshClient.startSession();
        try {

            session.allocateDefaultPTY();


            shell = session.startShell();

             StreamCopier sc = new StreamCopier(shell.getInputStream(), System.out);
                   sc.bufSize(shell.getLocalMaxPacketSize())
                    .spawn("stdout");

            sc.listener(new StreamCopier.Listener() {
                @Override
                public void reportProgress(long transferred) throws IOException {
                    System.out.println("\n###### createIs : " + transferred);
                }
            });

            Event<IOException> event =  sc.spawnDaemon("xyp");


            System.out.println("################################### TEST");

            StreamCopier scerr = new StreamCopier(shell.getErrorStream(), System.err);
                    scerr.bufSize(shell.getLocalMaxPacketSize())
                    .spawn("stderr");

            scerr.listener(new StreamCopier.Listener() {
                @Override
                public void reportProgress(long transferred) throws IOException {
                    System.out.println("\ntrans-err: "+transferred);
                }
            });

            // Now make System.in act as stdin. To exit, hit Ctrl+D (since that results in an EOF on System.in)
            // This is kinda messy because java only allows console input after you hit return
            // But this is just an example... a GUI app could implement a proper PTY
//            new StreamCopier(System.in, shell.getOutputStream())
//                    .bufSize(shell.getRemoteMaxPacketSize())
//                    .copy();

            System.out.println("################################### BEST");

        } finally {
           // session.close();
        }
    }

    public void writeToPseudoTerminal(String cmd) throws IOException {
        InputStream is = new ByteArrayInputStream( (cmd+"\n").getBytes() );

        StreamCopier sc = new StreamCopier(is, shell.getOutputStream());
                sc.bufSize(shell.getRemoteMaxPacketSize())
                .copy();

        sc.listener(new StreamCopier.Listener() {
            @Override
            public void reportProgress(long transferred) throws IOException {
                System.out.println("\n####### write2pseudo: "+transferred);
            }
        });
    }
}
