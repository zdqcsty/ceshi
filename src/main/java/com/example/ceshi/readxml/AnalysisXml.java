package com.example.ceshi.readxml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AnalysisXml {

    public final static String XML_PATH= "./src/main/resources/liantong.xml";

    public static void main(String[] args) throws Exception {
        File file = new File(XML_PATH);
        XmlMapper xmlMapper = new XmlMapper();
        ClusterInfoList clusters = xmlMapper.readValue(file, ClusterInfoList.class);
        ClusterInfo info = getClusterInfo(clusters, "source");
        System.out.println(info.getName());
    }

    public static ClusterInfo getClusterInfo(ClusterInfoList clusters, String clusterName) throws Exception {
        List<ClusterInfo> clusterinfos = clusters.getClusterinfos();
        for (ClusterInfo info:clusterinfos){
            if(clusterName.equals(info.getName())){
                return info;
            }
        }
        throw new Exception("Not found cluster: " + clusterName);
    }

    public static ClusterInfoList getClusterInfoList(String path) throws IOException {
        File file = new File(path);
        XmlMapper xmlMapper = new XmlMapper();
        ClusterInfoList clusters = xmlMapper.readValue(file, ClusterInfoList.class);
        return clusters;
    }


}
