package de.maxel.remote.ssh;

import com.jcraft.jsch.*;


/**
 * Created by max on 18.09.15.
 * TODO: handle exceptions
 */
public final class SSHSession {

    private static SSHSession sshSession;
    public static SSHSession getInstance(String host, String user, String password) {
        if (sshSession == null)
            sshSession = new SSHSession(host, user, password);
        return sshSession;
    }
    public static SSHSession getInstance() {
        return sshSession;
    }
    private SSHSession(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    private Session session;
    //for later use in case of a session timeout
    private String host;
    private String user;
    private String password;

    /**
     * starts a ssh session
     */
    public void startSession() {
        try{
            initializeSession();
            System.out.println("session started...");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * ends a ssh session
     */
    public void endSession() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("session terminated...");
        }
    }

    /**
     * initialize the session in case of an timeout
     * @return the session or null if there are missing login details
     */
    public Session getSession() {
        if (session != null && session.isConnected())
            return session;

        if (host != null && !host.isEmpty() &&
            user != null && !user.isEmpty() &&
            password != null && !password.isEmpty())
            initializeSession();
        else
            return null;

        return session;

    }

    private void initializeSession() {
        JSch jsch=new JSch();
        try {
            session=jsch.getSession(user, host, 22);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        session.setPassword(password);

        session.setUserInfo(new MyUserInfo());
        try {
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

        private static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
        public String getPassword(){ return null; }
        //TODO: the user has to be ask if it is an unknown key
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

