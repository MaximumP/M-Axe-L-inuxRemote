package de.maxel.remote.jetty.rest;

import de.maxel.remote.jetty.rest.model.SSHUserModel;
import de.maxel.remote.ssh.SshClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by max on 12.10.15.
 *
 * initiates and ends a ssh session
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginManager {

    /**
     *
     * @param userModel contains the username, password and host
     * @return The state of the login action
     */
    @POST
    public String login(SSHUserModel userModel) {
        try {
            SshClient.getInstance().connect(userModel);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"state\": \"failure\"}";
        }
        return "{\"state\": \"success\"}";
    }

    /**
     *
     * @return the state of the logout action
     */
    @POST
    @Path("/logout")
    public String logout() {
        try {
            SshClient.getInstance().disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"state\": \"failure\"}";
        }
        return "{\"state\": \"success\"}";
    }
}
