package de.maxel.remote.jetty.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by max on 12.10.15.
 */

@Path("/software")
public class SoftwareManager {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@PathParam("penis") String penis) {
        return "Ok";
    }
}
