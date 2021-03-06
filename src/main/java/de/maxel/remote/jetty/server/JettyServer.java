package de.maxel.remote.jetty.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

/**
 * Created by max on 18.09.15.
 *
 * Jetty Server wrapper
 */
public class JettyServer {

    private Server server;

    public JettyServer() {
        this(8585);
    }

    public JettyServer(Integer runningPort) {
        server = new Server(runningPort);
    }

    public void setHandler(ContextHandlerCollection contexts) {
        server.setHandler(contexts);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
        server.join();
    }

    public boolean isRunning() {
        return server.isRunning();
    }
}
