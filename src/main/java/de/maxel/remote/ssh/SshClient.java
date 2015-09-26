package de.maxel.remote.ssh;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;

import java.io.IOException;

/**
 * Created by Alexander on 26.09.2015.
 */
public class SshClient {

    private SSHClient sshClient = null;
    private String workingDirectory = "~/";

    /**
     * author Alexander
     * Thread-Safe lazy singleton
     * more about it : http://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-with-examples
     */
    private static class Holder {
        static final SshClient instance = new SshClient();
    }

    /**
     * author Alexander
     *
     * @return `s a thread-safe lazy loaded singleton instance of this class
     */
    public static SshClient getInstance() {
        return Holder.instance;
    }

    public void connect(String host, String username, String password, String hostKey) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SSHClient getSshClient(){
        return this.sshClient;
    }
}
