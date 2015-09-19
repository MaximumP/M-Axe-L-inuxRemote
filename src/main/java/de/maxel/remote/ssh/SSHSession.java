package de.maxel.remote.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import de.maxel.remote.ssh.schell.commands.Command;
import de.maxel.remote.ssh.schell.commands.Ls;

import javax.swing.*;

/**
 * Created by max on 18.09.15.
 */
public final class SSHSession {
    public static void startSession(String host, String user, String password) {
        try{
            JSch jsch=new JSch();
            com.jcraft.jsch.Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);

            session.setUserInfo(new MyUserInfo());
            session.connect();

            Command command = new Ls(session);
            command.addArgument("lF");
            String commandResult = command.executeCommand();

            System.out.print(commandResult);

            session.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
        public String getPassword(){ return null; }
        public boolean promptYesNo(String str){
            return true;
        }
        public String getPassphrase(){ return null; }
        public boolean promptPassphrase(String message){ return true; }
        public boolean promptPassword(String message){ return true;}
        public void showMessage(String message){ }
        public String[] promptKeyboardInteractive(String destination,
                                                  String name,
                                                  String instruction,
                                                  String[] prompt,
                                                  boolean[] echo){

            return null;
        }
    }
}

