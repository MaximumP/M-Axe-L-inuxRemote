package de.maxel.remote.jetty.rest;

import de.maxel.remote.jetty.rest.model.RestResponse;
import de.maxel.remote.jetty.rest.model.SoftwareSearchList;
import de.maxel.remote.ssh.MaxelShell;
import de.maxel.remote.ssh.SshClient;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by max on 12.10.15.
 */

@Path("/software")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SoftwareManager {

    @GET
    @Path("/search/{searchString}")
    public RestResponse searchPackage(@PathParam("searchString") String searchString) {
        RestResponse response = new RestResponse();
        SSHClient sshClient = SshClient.getInstance().getSshClient();
        try {
            Session session = sshClient.startSession();
            Session.Command command = session.exec("apt-cache search " + searchString);
            String sshResponse = IOUtils.readFully(command.getInputStream()).toString();
            command.close();
            session.close();
            response.setResponseState(RestResponse.ResponseState.Success);
            response.setMessage("Fetched software list");
            response.setModel(new SoftwareSearchList(sshResponse));
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            response.setResponseState(RestResponse.ResponseState.Error);
            response.setMessage("Error while fetching software list: \n" + e.getMessage());
            return response;
        }
    }

    @PUT
    @Path("/install/{packageName}")
    public RestResponse installPackage(@PathParam("packageName") String packageName){
        //TODO: install multiple programs via body object
        RestResponse response = new RestResponse();
        SSHClient client = SshClient.getInstance().getSshClient();
        try {
            Session session = client.startSession();
            MaxelShell shell = new MaxelShell(null);
            shell.startShell(session);
            session.close();

            response.setResponseState(RestResponse.ResponseState.Success);
            response.setMessage("Package: " + packageName + " installed.\n");
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void readToPrompt(InputStream inputStream) {

        String pattern = "[sudo] password for max:";
        while(true) {
            try {
                char content;
                for (int i = 0; i < pattern.length(); i++) {
                    content = (char)inputStream.read();
                    System.out.print(content);
                    if (content == pattern.charAt(i)){
                        if (i == pattern.length()-1) {
                            return;
                        }
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @POST
    @Path("/update")
    public RestResponse update(){
        return null;
    }

    @POST
    @Path("/upgrade")
    public RestResponse upgrade(){
        return null;
    }

    @DELETE
    @Path("/remove")
    public RestResponse remove(){
        //TODO: remove multiple programs via body object?
        return null;
    }
}
