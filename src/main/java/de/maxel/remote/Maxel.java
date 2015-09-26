package de.maxel.remote;

import de.maxel.remote.config.ConfigProperties;
import de.maxel.remote.jetty.context.AppContextBuilder;
import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.jetty.ui.ServerRunner;
import de.maxel.remote.ssh.schell.commands.Shell;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by max on 19.09.15.
 */
public class Maxel {

    public static void main(String[] arg) {
        //Load Properties
        loadPropsTmp();
        ConfigProperties properties = ConfigProperties.getInstance();

        Shell shell = new Shell(properties.getUser(), properties.getHost(), properties.getPassword());

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{new AppContextBuilder().buildWebAppContext()});

        final JettyServer jettyServer = new JettyServer();
        jettyServer.setHandler(contexts);

        Runnable runner = () -> new ServerRunner(jettyServer);
        EventQueue.invokeLater(runner);
    }

    private static void loadPropsTmp(){
        try {
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream("maxel.properties");
            prop.load(input);
           ConfigProperties.getInstance().init(prop);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
