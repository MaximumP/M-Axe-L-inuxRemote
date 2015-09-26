package de.maxel.remote.jetty.ui;

import de.maxel.remote.jetty.server.JettyServer;

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
                    server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Stop Jetty Hook"));
    }
}
