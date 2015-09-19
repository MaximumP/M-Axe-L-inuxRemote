package de.maxel.remote.jetty.ui;

import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.ssh.SSHSession;

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
            SSHSession session = SSHSession.getInstance();
            if (session != null)
                session.endSession();

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
