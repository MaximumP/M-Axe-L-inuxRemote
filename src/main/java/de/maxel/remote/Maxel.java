package de.maxel.remote;

import de.maxel.remote.jetty.server.AppContextBuilder;
import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.jetty.server.ServerRunner;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.awt.*;

/**
 * Created by max on 19.09.15.
 *
 * Program start
 */
public class Maxel {

    public static void main(String[] arg) {
        //Load Properties
        //loadPropsTmp();
        /*ConfigProperties properties = ConfigProperties.getInstance();

        SshClient.getInstance().connect(properties.getHost(), properties.getUser(),
                properties.getPassword(), properties.getHostkey());


        SSHJsftp testsFtp = new SSHJsftp(SshClient.getInstance().getSshClient());
        testsFtp.cd("..");
        testsFtp.cd("..");
        testsFtp.cd("etc");*/

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{new AppContextBuilder().buildWebAppContext()});

        final JettyServer jettyServer = new JettyServer();
        jettyServer.setHandler(contexts);

        Runnable runner = () -> new ServerRunner(jettyServer);
                EventQueue.invokeLater(runner);
    }

    /*private static void loadPropsTmp(){
        try {
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream("maxel.properties");
            prop.load(input);
           ConfigProperties.getInstance().init(prop);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
