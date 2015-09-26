package de.maxel.remote.ssh;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.StreamCopier;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Alexander on 26.09.2015.
 */
public class SshPTYManager {

    private SSHClient sshClient = null;
    private Session session = null;
    private Session.Shell shell = null;

    public SshPTYManager(SSHClient client) throws ConnectionException, TransportException {
        this.sshClient = client;

        session = sshClient.startSession();

        session.allocateDefaultPTY();

        shell = session.startShell();

        new StreamCopier(shell.getInputStream(), System.out)
                .bufSize(shell.getLocalMaxPacketSize())
                .spawn("stdout");

        new StreamCopier(shell.getErrorStream(), System.err)
                .bufSize(shell.getLocalMaxPacketSize())
                .spawn("stderr");

        // Now make System.in act as stdin. To exit, hit Ctrl+D (since that results in an EOF on System.in)
        // This is kinda messy because java only allows console input after you hit return
        // But this is just an example... a GUI app could implement a proper PTY


    }

    public void writeCmd(String cmd) throws IOException {
        InputStream is = new ByteArrayInputStream((cmd + "\n").getBytes(StandardCharsets.UTF_8));

        new StreamCopier(is, shell.getOutputStream())
                .bufSize(shell.getRemoteMaxPacketSize())
                .copy();
    }

    public void closeSession() throws ConnectionException, TransportException {
        session.close();
    }
}
