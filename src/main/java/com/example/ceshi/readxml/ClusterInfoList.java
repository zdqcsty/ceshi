package com.example.ceshi.readxml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

import java.util.List;

/**
 *
 *  @JacksonXmlElementWrapper(localName = "Students")
 *     @JacksonXmlProperty(localName = "Stu")
 *     List<Student> student;  //学生列表
 *
 */


@Data
@JacksonXmlRootElement(localName = "liantong")
public class ClusterInfoList {

//    @JacksonXmlElementWrapper(localName = "Students")
    @JacksonXmlText
    public List<ClusterInfo> clusterinfos;

    public ClusterInfoList(){}

}
