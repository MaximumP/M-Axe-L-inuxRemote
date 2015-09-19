package de.maxel.remote.jetty.ui;

import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.ssh.SSHSession;

/**
 * Created by max on 18.09.15.
 *
 * Swing ui to start and stop the server
 */
public class ServerRunner {

    public ServerRunner(final JettyServer server) {
        SSHSession sshSession = SSHSession.getInstance();
        if (sshSession != null)
            sshSession.endSession();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server.isRunning()) {
                try {
                    server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Stop Jetty Hook"));
    }
}
