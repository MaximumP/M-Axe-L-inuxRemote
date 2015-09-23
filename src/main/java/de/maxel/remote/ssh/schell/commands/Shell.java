package de.maxel.remote.ssh.schell.commands;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Scanner;

/**
 * Created by max on 20.09.15.
 *
 * Emulates an shell of the remote host
 */
public class Shell {

    private Session session;
    private Channel channel;

    //output stream to write to the remote shell
    private PipedOutputStream out;
    //input stream the remote shell writes in
    private PipedInputStream in;

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
            channel.setOutputStream(outputStream);
            channel.connect();
            read();
            listenToTerminal();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * http://stackoverflow.com/a/26473083/1847899
     */
    private void listenToTerminal(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String x = scanner.nextLine();
            write(x);
        }
    }

    /**
     * sends a command to the shell
     * @param command: the command to be executed on the remove shell
     *                 a new line has to be used to separate commands
     */
    public void write(String command) {
        if (!command.endsWith("\n"))
            command = command + "\n";
        try {
            out.write(command.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * reads the result of the executed commands
     */
    private void read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        //read in a new thread since we want to listen as long as the user uses "his" shell
        Thread readThread = new Thread(() -> {
            try {
                while (true) {
                    reader.mark(5);
                    //TODO check
                    //end of stream. I am not sure if this works
                    int tmp = reader.read();
                    if (tmp == -1) {
                        reader.close();
                        break;
                    }
                    else {
                        reader.reset();
                        String line = reader.readLine();
                        //debug output
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readThread.start();
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
        public void showMessage(String s) {}
    }
}
