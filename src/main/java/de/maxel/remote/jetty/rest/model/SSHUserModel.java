package de.maxel.remote.jetty.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by max on 12.10.15.
 *
 * contains the ssh user credentials
 */
@XmlRootElement
public class SSHUserModel {

    private String user;
    private String password;
    private String host;
    private int port;

    // Default constructor for json mapping
    public SSHUserModel() {}

    public SSHUserModel(String user, String password, String host) {
        this(user, password, host, 22);
    }

    public SSHUserModel(String user, String password, String host, int port) {
        this.user = user;
        this.password = password;
        this.host     = host;
        this.port     = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
