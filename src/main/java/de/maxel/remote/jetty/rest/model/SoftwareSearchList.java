package de.maxel.remote.jetty.rest.model;

import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
import javafx.util.Pair;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by max on 16.10.15.
 */
@XmlRootElement
public class SoftwareSearchList {
    private List<Pair<String, String>> softwareList;

    public SoftwareSearchList(){}
    public SoftwareSearchList(String searchResult){
        setSoftwareList(searchResult);
    }

    public void setSoftwareList(String searchResult) {
        softwareList = new ArrayList<>();
        String[] packages = searchResult.split("\n");
        for (String aPackage : packages) {
            String[] packageName = aPackage.split(" - ");
            if(packageName.length>1) {
                softwareList.add(new Pair<>(packageName[0], packageName[1]));
            }else{
                softwareList.add(new Pair<>("", ""));
            }
        }
    }

    public List<Pair<String, String>> getSoftwareList() {
        return softwareList;
    }
}
