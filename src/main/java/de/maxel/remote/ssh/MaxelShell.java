package de.maxel.remote.ssh;

import de.maxel.remote.jetty.rest.model.SSHUserModel;
import jdk.internal.util.xml.impl.Input;
import net.schmizz.sshj.common.StreamCopier;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by max on 17.10.15.
 */
public class MaxelShell {

    private Session.Shell shell;
    private SSHUserModel user;

    public MaxelShell(SSHUserModel user) {
        this.user = user;
    }

    public void startShell(Session session) {
        try {
            session.allocateDefaultPTY();
            shell = session.startShell();
            readToPrompt("max@max-z575:~$ ", shell.getInputStream());
            new StreamCopier(shell.getErrorStream(), System.out);
            new StreamCopier(System.in, shell.getOutputStream());
            readToPrompt("[sudo] password for max: ", shell.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readToPrompt(String expectedString, InputStream iStream) {
        char actChar;
        while(true) {
            for (int i = 0; i < expectedString.length(); i++) {
                try {
                    actChar = (char)iStream.read();
                    System.out.print(actChar);
                    if (actChar == expectedString.charAt(i)) {
                        if (i == expectedString.length() -1)
                            return;
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
