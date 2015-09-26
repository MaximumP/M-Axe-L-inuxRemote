package de.maxel.remote.ssh.schell.commands;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by max on 20.09.15.
 * <p>
 * Emulates an shell of the remote host
 */
public class Shell {

    private Session session;
    private Channel channel;

    //output stream to write to the remote shell
    private PipedOutputStream out;
    //input stream the remote shell writes in
    private PipedInputStream in;

    private boolean resultReceived = false;
    private StringBuilder resultBuilder = new StringBuilder();

    public Shell(String userName, String host, String password) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(userName, host);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        session.setPassword(password);
        initializeShell();
    }

    /**
     * Initialize the shell
     */
    private void initializeShell() {
        try {

            session.setUserInfo(new ShellUserInfo());
            session.connect();
            //open a shell channel
            channel = session.openChannel("shell");

            //create a input stream which "listen" to our output stream
            final InputStream inputStream = new PipedInputStream();
            //connect the output stream we use to write to the input stream of the shell
            out = new PipedOutputStream((PipedInputStream) inputStream);
            channel.setInputStream(inputStream);

            //create a output stream the shell can write to
            final OutputStream outputStream = new PipedOutputStream();
            //receives the data of the shell
            in = new PipedInputStream((PipedOutputStream) outputStream);
            channel.setOutputStream(outputStream, false);
            channel.connect();
            read();
            write("PS1=\"END\" && stty -echo\n");
            // wait till the login message is received and clear the buffer afterwards
            waitForReader();
            //remove user@machine message and turn of the echo so no command will be written

            resultBuilder.setLength(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * http://stackoverflow.com/a/26473083/1847899
     */
    private void listenToTerminal() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String x = scanner.nextLine();
            write(x);
        }
    }

    /**
     * sends a command to the shell
     *
     * @param command: the command to be executed on the remove shell
     *                 a new line has to be used to separate commands
     */
    public String write(String command) {
        if (!command.endsWith("\n"))
            command = command + "\n";
        try {
            setResultReceived(false);
            out.write(command.getBytes());
            waitForReader();
            System.out.println("LAST?! " + command + "\n");
            return resultBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * reads the result of the executed commands
     */
    private void read() {  
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        //read in a new thread since we want to listen as long as the user uses "his" shell
        Thread readThread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                while (true) {
                    setResultReceived(resultBuilder.toString().endsWith("END"));
                    if (resultReceived) {
                        System.out.println("Notified!\n");
                        notifyWriter();
                    }
                    char bla = (char)reader.read();
                    System.out.print(bla);
                    resultBuilder.append(bla);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        readThread.start();
    }

    private synchronized void notifyWriter() {
        notifyAll();
    }

    private synchronized void setResultReceived(boolean value) {
        resultReceived = value;
    }

    private synchronized void waitForReader() {
        while (!resultReceived) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * closes the shell and our streams
     */
    public void close() {
        write("exit\n");
        channel.disconnect();
        session.disconnect();
        try {
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ShellUserInfo implements UserInfo {

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String s) {
            return false;
        }

        @Override
        public boolean promptPassphrase(String s) {
            return false;
        }

        @Override
        public boolean promptYesNo(String s) {
            return true;
        }

        @Override
        public void showMessage(String s) {
        }
    }
}
