package com.example.ceshi.readxml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.*;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws IOException {

        String xmlPath="/home/hadoop/zgh/book.xml";
        String name="dbaaa";
        ConnectionInfo info = analysisXml(name, xmlPath);
        generalLoadDataShell(info,"table","aaa","bbb");

        try {
            String cmd="sh /home/hadoop/zgh/demo.sh";
            Process process = Runtime.getRuntime().exec(cmd);
            int status = process.waitFor();
            if(status != 0){
                System.err.println("Failed to call shell's command and the return status's is: " + status);
            }else {
                System.out.println("sucess---------");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 解析xml 并返回包含连接信息的map对象
     * @param name
     * @param path
     * @return
     * @throws IOException
     */
    public static ConnectionInfo analysisXml(String name,String path) throws IOException {
        ConnectionInfo info=new ConnectionInfo();
        File file = new File(path);
        XmlMapper xmlMapper = new XmlMapper();
        LianTong value = xmlMapper.readValue(file, LianTong.class);
        List<ConnectionInfo> conns = value.getConnections();
        for (ConnectionInfo conn:conns){
            if (name.equals(conn.getName())){
                info.setDbtype(conn.getDbtype());
                info.setPort(conn.getPort());
                info.setHost(conn.getHost());
                info.setName(conn.getName());
                info.setUser(conn.getUser());
                info.setPasswd(conn.getPasswd());
                return info;
            }
        }
        return null;
    }


    public static boolean generalLoadDataShell(ConnectionInfo info,String tableName,String csvLocation,String hdfsLocation) throws FileNotFoundException {

        String path="/home/hadoop/zgh/loadData.sh";
        PrintWriter pw = new PrintWriter(path);
        pw.println("#!/bin/bash");
        pw.println("export NLS_LANG=AMERICAN_AMERICA.AL32UTF8");
        pw.println("ORACLE_BASE=/home/deployer/oracle;export ORACLE_BASE");
        pw.println("ORACLE_HOME=/home/deployer/oracle/app;export ORACLE_HOME");
        pw.println("LD_LIBRARY_PATH=$ORACLE_HOME/lib:$ORACLE_HOME/lib:/lib:/usr/lib:.;export LD_LIBRARY_PATH");
        pw.println("ORA_NLS33=$ORACLE_HOME/ocommon/nls/admin/data;export ORA_NLS33");
        pw.println("TMPDIR=/tmp;export TMPDIR");
        pw.println("PATH=$ORACLE_HOME/bin:$PATH;export PATH");
        pw.println("");
        pw.println("");
        pw.println("");

        String user = info.getUser();
        String passwd = info.getPasswd();
        String host = info.getHost();
        //导入语句
        String loadStat="sqluldr user="+user+"/"+passwd+"@"+host+" QUERY=\"SELECT * FROM "+tableName+"\"  file=\""+csvLocation+"\" field=, record=0x0a quote=0x22 charset=utf8 safe=yes head=yes fast=yes";
        pw.println(loadStat);
        pw.println("hadoop fs -put "+csvLocation+" "+hdfsLocation+"");
        pw.println("hadoop fs -touchz "+hdfsLocation+"/_SUCCESS");

        pw.close();
        return true;
    }
}
