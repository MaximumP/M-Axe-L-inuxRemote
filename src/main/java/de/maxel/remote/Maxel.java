package de.maxel.remote;

import de.maxel.remote.jetty.context.AppContextBuilder;
import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.jetty.ui.ServerRunner;
import de.maxel.remote.ssh.schell.commands.Shell;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by max on 19.09.15.
 */
public class Maxel {
    public static void main(String[] arg) {

        /*Shell shell = new Shell("max", "localhost", "duewMW78");

        shell.write("pwd\n");
        shell.write("cd Music\n");
        shell.write("pwd\n");*/

        Shell shell = new Shell("root", "37.221.196.247", "vmghhU8mQsb7TEX");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{new AppContextBuilder().buildWebAppContext()});

        final JettyServer jettyServer = new JettyServer();
        jettyServer.setHandler(contexts);

        Runnable runner = () -> new ServerRunner(jettyServer);
        EventQueue.invokeLater(runner);
    }
}
