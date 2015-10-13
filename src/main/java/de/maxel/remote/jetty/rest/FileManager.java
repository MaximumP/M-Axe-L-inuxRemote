package de.maxel.remote.jetty.rest;

import de.maxel.remote.jetty.rest.model.DirectoryModel;
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
    public DirectoryModel ls(@PathParam("destination") String destination) {
        DirectoryModel directoryModel = null;
        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            directoryModel = new DirectoryModel();
            directoryModel.setCurrentDir(destination);
            List<RemoteResourceInfo> dirList = sftpClient.ls(destination);
            directoryModel.setDirContent(dirList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directoryModel;
    }

    /**
     * Creates a folder
     * @param folder absolute path to the folder to create
     */
    @POST
    @Path("/mkdir/{folder}")
    public String mkdir(@PathParam("folder") String folder) {

        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            sftpClient.mkdir(folder);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"state\": \"failure\"}";
        }
        return "{\"state\": \"success\"}";
    }

    /**
     * renames a folder
     * @param oldName absolute path to the folder to rename
     * @param newName absolute path with the new name
     */
    @PUT
    @Path("/rename/{oldname}/{newname}")
    public String rename(@PathParam("oldname") String oldName,
                       @PathParam("newname") String newName) {
        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            sftpClient.rename(oldName, newName);
        }catch (IOException e) {
            e.printStackTrace();
            return "{\"state\": \"failure\"}";
        }
        return "{\"state\": \"success\"}";
    }

    /**
     * Deletes a folder
     * @param folder absolute path to the folder to delete
     */
    @DELETE
    @Path("/rmdir/{folder}")
    public String rmdir(@PathParam("folder") String folder) {

        try {
            SSHClient sshClient = SshClient.getInstance().getSshClient();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            sftpClient.rmdir(folder);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"state\": \"failure\"}";
        }
        return "{\"state\": \"success\"}";
    }
}
