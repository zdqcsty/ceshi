package com.example.ceshi.readxml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JacksonXml {

    public static final String XML_PATH = "src/main/resources/clustersinfo.xml";

    public static void main(String[] args) throws IOException {

        XmlMapper mapper = new XmlMapper();
        File file = new File(XML_PATH);
        ClusterInfoList clusterInfoList = mapper.readValue(file, ClusterInfoList.class);

        List<ClusterInfo> clusterinfos = clusterInfoList.getClusterinfos();
        for (ClusterInfo info : clusterinfos) {
            System.out.println(info.getName());
        }
    }
}
