package de.maxel.remote.jetty.rest;

import de.maxel.remote.jetty.rest.model.SSHUserModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by max on 12.10.15.
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginManager {

    @GET
    public SSHUserModel getLoginForm() {
        return new SSHUserModel("max", "123qwe", "localhost");
    }


}
