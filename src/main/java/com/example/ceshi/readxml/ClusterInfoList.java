package com.example.ceshi.readxml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

import java.util.List;

/**
 * @JacksonXmlElementWrapper(localName = "Students")
 * @JacksonXmlProperty(localName = "Stu")
 * List<Student> student;  //学生列表
 */

@JacksonXmlRootElement(localName = "clusterinfos")
public class ClusterInfoList {
    @JacksonXmlElementWrapper(localName = "demo", useWrapping = false)
    @JacksonXmlProperty(localName = "cluster")
    public List<ClusterInfo> clusterinfos;

    public List<ClusterInfo> getClusterinfos() {
        return clusterinfos;
    }

    public void setClusterinfos(List<ClusterInfo> clusterinfos) {
        this.clusterinfos = clusterinfos;
    }

    public ClusterInfoList() {
    }
}
