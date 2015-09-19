import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import remote.runjetty.context.AppContextBuilder;
import remote.runjetty.server.JettyServer;
import remote.runjetty.ui.ServerRunner;
import ssh.SSHSession;

import java.awt.*;

/**
 * Created by max on 17.09.15.
 */
public class Test{
    public static void main(String[] arg) {
        //SSHSession.startSession();

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{new AppContextBuilder().buildWebAppContext()});

        final JettyServer jettyServer = new JettyServer();
        jettyServer.setHandler(contexts);

        Runnable runner = new Runnable() {
            @Override
            public void run() {
                new ServerRunner(jettyServer);
            }
        };
        EventQueue.invokeLater(runner);
    }
}