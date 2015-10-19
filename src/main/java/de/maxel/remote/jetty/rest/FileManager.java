package de.maxel.remote.jetty.rest;

import de.maxel.remote.jetty.rest.model.DirectoryModel;
import de.maxel.remote.jetty.rest.model.RestResponse;
import de.maxel.remote.ssh.SshClient;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Created by max on 12.10.15.
 *
 * offers basic file manager methods
 */
@Path("/filemanager")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FileManager {

    /**
     * Gets the directory content
     * @param destination absolute path to a folder
     * @return the directory content
     */
    @GET
    @Path("/ls/{destination}")
    public RestResponse ls(@PathParam("destination") String destination) {
        RestResponse response = new RestResponse();
        DirectoryModel directoryModel;
        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            directoryModel = new DirectoryModel();
            directoryModel.setCurrentDir(destination);
            List<RemoteResourceInfo> dirList = sftpClient.ls(destination);
            sftpClient.close();

            directoryModel.setDirContent(dirList);
            response.setResponseState(RestResponse.ResponseState.Success);
            response.setModel(directoryModel);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setMessage("Error while reading file system: \n" + e.getMessage());
            response.setResponseState(RestResponse.ResponseState.Error);
            return response;
        }
    }

    /**
     * Creates a folder
     * @param folder absolute path to the folder to create
     */
    @POST
    @Path("/mkdir/{folder}")
    public RestResponse mkdir(@PathParam("folder") String folder) {
        RestResponse response = new RestResponse();
        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            sftpClient.mkdir(folder);
            sftpClient.close();
            String folderName = folder.substring(folder.lastIndexOf('/'));
            response.setResponseState(RestResponse.ResponseState.Success);
            response.setMessage("Created folder: " + folderName);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setMessage("Error while creating directory: \n" + e.getMessage());
            response.setResponseState(RestResponse.ResponseState.Error);
            return response;
        }
    }

    /**
     * renames a folder
     * @param oldName absolute path to the folder to rename
     * @param newName absolute path with the new name
     */
    @PUT
    @Path("/rename/{oldname}/{newname}")
    public RestResponse rename(@PathParam("oldname") String oldName,
                       @PathParam("newname") String newName) {
        RestResponse response = new RestResponse();
        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            sftpClient.rename(oldName, newName);
            sftpClient.close();
            String oldFolderName = oldName.substring(oldName.lastIndexOf('/'));
            String newFolderName = newName.substring(newName.lastIndexOf('/'));
            response.setResponseState(RestResponse.ResponseState.Success);
            response.setMessage("Folder " + oldFolderName + "renamed to " + newFolderName);
            //TODO: return new model? or handle it on the client side?
            return response;
        }catch (IOException e) {
            e.printStackTrace();
            response.setResponseState(RestResponse.ResponseState.Error);
            response.setMessage("Error while renaming folder: \n" + e.getMessage());
            return response;
        }
    }

    /**
     * Deletes a folder
     * @param folder absolute path to the folder to delete
     */
    @DELETE
    @Path("/rmdir/{folder}")
    public RestResponse rmdir(@PathParam("folder") String folder) {
        RestResponse response = new RestResponse();
        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            sftpClient.rmdir(folder);
            sftpClient.close();
            String folderName = folder.substring(folder.lastIndexOf('/'));
            response.setResponseState(RestResponse.ResponseState.Success);
            response.setMessage("Folder " + folderName + "deleted");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setResponseState(RestResponse.ResponseState.Error);
            response.setMessage("Error while deleting folder: \n" + e.getMessage());
            return response;
        }
    }
}
