package de.maxel.remote.jetty.ui;

import de.maxel.remote.jetty.server.JettyServer;

/**
 * Created by max on 18.09.15.
 *
 * Swing ui to start and stop the server
 */
public class ServerRunner {

    public ServerRunner(final JettyServer server) {
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
