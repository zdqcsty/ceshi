package com.example.ceshi.readxml;

public class ConnectionInfo {
    private String  name;
    private String  dbtype;
    private String  user;
    private String  passwd;
    private String  host;
    private int  port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ConnectionInfo(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "name='" + name + '\'' +
                ", dbtype='" + dbtype + '\'' +
                ", user='" + user + '\'' +
                ", passwd='" + passwd + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
