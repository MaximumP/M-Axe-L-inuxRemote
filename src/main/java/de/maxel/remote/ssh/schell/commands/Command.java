package de.maxel.remote.ssh.schell.commands;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import de.maxel.remote.ssh.SSHSession;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by max on 18.09.15.
 */
public class Command {

    private String command;

    public Command(String command) {
        this.command = command;
    }

    public String executeCommand() {

        StringBuilder retVal = new StringBuilder();
        Session session = SSHSession.getInstance().getSession();

        try {
            Channel channel = session.openChannel("exec");
            String execCommand = command;
            ((ChannelExec)channel).setCommand(execCommand);
            ((ChannelExec)channel).setErrStream(System.err);
            channel.setInputStream(null);

            InputStream inputStream = channel.getInputStream();
            channel.connect();

            byte[] tmp=new byte[1024];
            while(true){
                while(inputStream.available()>0){
                    int i=inputStream.read(tmp, 0, 1024);
                    if(i<0)break;
                    retVal.append(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    if(inputStream.available()>0)
                        continue;
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal.toString();
    }
}
