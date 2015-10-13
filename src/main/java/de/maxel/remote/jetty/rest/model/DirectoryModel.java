package de.maxel.remote.jetty.rest.model;

import net.schmizz.sshj.sftp.RemoteResourceInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by max on 12.10.15.
 *
 * POJO Object for a directory
 * is serialized to a json or xml by the javax.ws library
 */
@XmlRootElement
public class DirectoryModel {

    private String currentDir;
    private List<RemoteResourceInfo> dirContent;

    public void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }

    public void setDirContent(List<RemoteResourceInfo> dirContent) {
        this.dirContent = dirContent;
    }

    public String getCurrentDir() {
        return currentDir;
    }

    public List<RemoteResourceInfo> getDirContent() {
        return dirContent;
    }
}
