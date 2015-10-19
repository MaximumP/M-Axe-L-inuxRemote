package de.maxel.remote.jetty.rest;

import de.maxel.remote.jetty.rest.model.RestResponse;
import de.maxel.remote.jetty.rest.model.SSHUserModel;
import de.maxel.remote.ssh.SshClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by max on 12.10.15.
 *
 * provides methods to login and logout of a ssh session
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginManager {

    /**
     * Initiates a ssh session to a remote machine
     *
     * @param userModel contains the username, password and host
     * @return The state of the login action
     */
    @POST
    @Path("/login")
    public RestResponse login(SSHUserModel userModel) {
        RestResponse response = new RestResponse();
        try {
            SshClient.getInstance().connect(userModel);
            response.setResponseState(RestResponse.ResponseState.Success);
            response.setMessage("Connected to " + userModel.getUser() + "@" + userModel.getHost());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setResponseState(RestResponse.ResponseState.Error);
            response.setMessage("Error wile connecting to Server: \n" + e.getMessage());
            return response;
        }
    }

    /**
     * Closes the ssh connection
     *
     * @return the state of the logout action
     */
    @POST
    @Path("/logout")
    public RestResponse logout() {
        RestResponse response = new RestResponse();
        try {
            SshClient.getInstance().disconnect();
            response.setResponseState(RestResponse.ResponseState.Success);
            response.setMessage("Disconnected");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setResponseState(RestResponse.ResponseState.Error);
            response.setMessage("Error while disconnecting: \n" + e.getMessage());
            return response;
        }
    }
}
