package de.maxel.remote;

import de.maxel.remote.config.ConfigProperties;
import de.maxel.remote.ssh.SSHJsftp;
import de.maxel.remote.ssh.SshClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by max on 19.09.15.
 *
 * Program start
 */
public class Maxel {

    public static void main(String[] arg) {
        //Load Properties
        loadPropsTmp();
        ConfigProperties properties = ConfigProperties.getInstance();

        SshClient.getInstance().connect(properties.getHost(), properties.getUser(),
                properties.getPassword(), properties.getHostkey());


        SSHJsftp testsFtp = new SSHJsftp(SshClient.getInstance().getSshClient());

        /*ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{new AppContextBuilder().buildWebAppContext()});

        final JettyServer jettyServer = new JettyServer();
        jettyServer.setHandler(contexts);

        Runnable runner = () -> new ServerRunner(jettyServer);
        EventQueue.invokeLater(runner);*/
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
