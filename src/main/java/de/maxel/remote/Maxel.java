package de.maxel.remote;

import de.maxel.remote.jetty.context.AppContextBuilder;
import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.jetty.ui.ServerRunner;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.awt.*;

/**
 * Created by max on 19.09.15.
 *
 * Application start
 */
public class Maxel {
    public static void main(String[] arg) {
        //SSHSession.startSession();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{new AppContextBuilder().buildWebAppContext()});

        final JettyServer jettyServer = new JettyServer();
        jettyServer.setHandler(contexts);
        try {
            jettyServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runnable runner = () -> new ServerRunner(jettyServer);
        EventQueue.invokeLater(runner);
    }
}
