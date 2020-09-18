package com.example.ceshi.readxml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ClusterInfo {
    private String name;

    public ClusterInfo(){};
}
