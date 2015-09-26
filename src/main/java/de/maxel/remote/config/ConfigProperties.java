package de.maxel.remote.config;

import java.io.File;
import java.util.Properties;

/**
 * Created on 23.09.2015
 * Author: Alexander Pfeif
 * <p/>
 * Copyright:
 */
public class ConfigProperties {

    //PROPERTY KEYS
    private static final String PASSWORD = "password";
    private static final String HOST     = "host";
    private static final String USER     = "user";
    private static final String HOSTKEY  = "hostkey";

    //DEFAULT LOGIN VALUES
    private String password   = "admin";
    private String host       = "host";
    private String user       = "user";
    private String hostkey = "hostkey";


    private Properties props = null; // Achtung werden nach dem Initialisieren auf Null gesetzt


    /**
     * author Alexander
     * Thread-Safe lazy singleton
     * more about it : http://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-with-examples
     */
    private static class Holder {
        static final ConfigProperties instance = new ConfigProperties();
    }

    /**
     * author Alexander
     *
     * @return `s a thread-safe lazy loaded singleton instance of this class
     */
    public static ConfigProperties getInstance() {
        return Holder.instance;
    }


    public final void init(Properties properties){
        this.props = new Properties(properties);

        loadInitialValues();

        this.props = null;
    }


    /**
     * init values from property file
     */
    private void loadInitialValues() {
        //login
        this.password = getOptionalString(PASSWORD,password);
        this.host = getOptionalString(HOST,host);
        this.user = getOptionalString(USER, user);
        this.hostkey = getOptionalString(HOSTKEY, hostkey);

    }

    public String getPassword() {
        return this.password;
    }

    public String getHost() {
        return this.host;
    }

    public String getUser() {
        return this.user;
    }

    public String getHostkey() { return this.hostkey; }

    //HELPER

    private String getOptionalString(String var1, String var2) {
        try {
            String var3 = props.getProperty(var1).trim();
            return var3;
        } catch (Exception var4) {
            return var2;
        }
    }

}
