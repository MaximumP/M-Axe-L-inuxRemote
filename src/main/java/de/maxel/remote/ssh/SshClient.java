package de.maxel.remote.ssh;

import de.maxel.remote.jetty.rest.model.SSHUserModel;
import net.schmizz.sshj.SSHClient;
import java.io.IOException;

/**
 * Created by Alexander on 26.09.2015.
 */
public class SshClient {

    private SSHClient sshClient = null;

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

    public void connect(SSHUserModel model) throws IOException {
        connect(model.getHost(),
                model.getUser(),
                model.getPassword(),
                "");
    }

    public void connect(String host, String username, String password, String hostKey) throws IOException {
        sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(hostKey);
        sshClient.connect(host);
        sshClient.authPassword(username, password);
    }

    public void disconnect() throws IOException{
        sshClient.disconnect();
    }

    public SSHClient getSshClient(){
        return this.sshClient;
    }
}
