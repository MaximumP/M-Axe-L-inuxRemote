package de.maxel.remote.ssh.schell.commands;

import com.jcraft.jsch.*;

import java.io.*;

/**
 * Created by max on 20.09.15.
 */
public class Shell {

    private String userName;
    private String host;
    private String password;
    private Session session;
    private Channel channel;

    private PipedOutputStream out;
    private PipedInputStream in;

    public Shell(String userName, String host, String password) {
        this.userName = userName;
        this.host = host;
        this.password = password;
        initializeShell();
    }

    private void initializeShell() {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(userName, host);
            session.setPassword(password);
            session.setUserInfo(new ShellUserInfo());
            session.connect(30000);

            //InputStream in = new PipedInputStream();
            //OutputStream out = new PipedOutputStream();
            //out = new PipedOutputStream();
            //in = new PipedInputStream(out);
            //read();
            channel = session.openChannel("shell");

            InputStream inputStream = new PipedInputStream();
            out = new PipedOutputStream((PipedInputStream)inputStream);
            channel.setInputStream(inputStream);

            OutputStream outputStream = new PipedOutputStream();
            in = new PipedInputStream((PipedOutputStream)outputStream);
            channel.setOutputStream(outputStream);

            channel.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Thread readThread = new Thread(() -> {
                while(true) {
                    try {
                        reader.mark(5);

                    int tmp = reader.read();
                    if (tmp == 0x003) {
                        throw new UnsupportedOperationException();
                        //break;
                    } else {
                        reader.reset();
                        System.out.println(reader.readLine());
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThread.start();


            out.write(new String("ls -lF\n").getBytes());

//            while (true){
//                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//                String bla = br.readLine();
//                write(bla==null?"":bla);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            writer.write(br.readLine());
            Thread.sleep(2000);
            Thread.sleep(2000);
                write("ls -f");

//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write(String command) {
        try {
            BufferedWriter input = new BufferedWriter(new OutputStreamWriter(out));
            input.write(command);
            input.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        StringBuilder stringBuilder = new StringBuilder();
        Thread readThread = new Thread(() -> {
            BufferedReader output = new BufferedReader(new InputStreamReader(in));
            boolean end = false;
            try {
                while (true) {
                    output.mark(32);
                    //int tmp = output.read();
                    //if (tmp == 109) end = true;
                    //else {
                        output.reset();
                        String line = output.readLine();
                        stringBuilder.append(line).append("\n");
                        System.out.println(line);
                        end = false;
                    //}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        readThread.start();
        return null;// stringBuilder.toString();
    }

    public String executeCommand(String command) {
        return null;
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
