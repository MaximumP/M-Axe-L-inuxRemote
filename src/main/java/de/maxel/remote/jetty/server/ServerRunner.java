package de.maxel.remote.jetty.server;

import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.ssh.SshClient;
import net.schmizz.sshj.SSHClient;

/**
 * Created by max on 18.09.15.
 *
 * Starts the server and register the cleanup shutdown hook
 */
public class ServerRunner {

    public ServerRunner(final JettyServer server) {
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server.isRunning()) {
                try {
                    // stop the jetty server
                    server.stop();
                    SSHClient client = SshClient.getInstance().getSshClient();
                    // close the ssh client
                    if (client != null && client.isConnected())
                        client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Stop Jetty Hook"));
    }
}
