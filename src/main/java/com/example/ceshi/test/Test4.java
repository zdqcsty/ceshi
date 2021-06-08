package com.example.ceshi.test;

import com.jcraft.jsch.*;

import java.util.Properties;

public class Test4 {

    public static void main(String[] args) throws JSchException {

        JSch jSch = new JSch();
        Session session = jSch.getSession("hadoop", "sftp://10.130.7.186:22", 22);
        session.setPassword("!QAZ2wsx@1234");
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        System.exit(0);
    }

}
