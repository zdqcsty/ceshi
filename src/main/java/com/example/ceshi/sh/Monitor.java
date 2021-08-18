/*
package com.example.ceshi.sh;

import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.client.api.YarnClient;

import java.io.FileReader;
import java.sql.Connection;
import java.util.Properties;
import java.util.UUID;

public class Monitor {

    public static final Log LOG = LogFactory.getLog(Monitor.class);
    private static final String APPLICATION_NAME = "thriftserver_" + UUID.randomUUID();

    public static void main(String[] args) throws Exception {
        //记录重启thrift的次数
        int flag = 0;

        String propPath = "thrift.properties";
        double percent = 0.6;

        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("propPath", true, "propPath");
        options.addOption("percent", true, "percent");
        CommandLine cl = parser.parse(options, args);
        if (cl.hasOption("propPath")) {
            propPath = cl.getOptionValue("propPath");
        }
        if (cl.hasOption("percent")) {
            percent = Double.parseDouble(cl.getOptionValue("percent"));
        }

        Properties prop = new Properties();
        prop.load(new FileReader(propPath));

        //生成脚本
        GenerateFile.generateStartThriftShell(percent, prop, APPLICATION_NAME);

        while (true) {
            if (checkThriftWork(prop)) {
                Thread.sleep(3000);
                continue;
            } else {
                kill(APPLICATION_NAME, prop);
                start(prop);
                LOG.info("thrift server has restart " + flag + " times");
                //等待thriftserver启动完成
                Thread.sleep(24000);
            }
        }
    }

    public static void start(Properties prop) throws Exception {

        //启动thriftserver
        boolean kerberos = Boolean.parseBoolean(prop.getProperty("kerberos"));
        String krb5Path = prop.getProperty("kerberps.krb5.path");
        String hadoopConfPath = prop.getProperty("hadoop.conf.dir");

        Runtime run = Runtime.getRuntime();
        if (kerberos) {
            String krb5Command = "cp " + krb5Path + " /etc";
            Process krb5Exec = run.exec(new String[]{"/bin/sh", "-c", "cp " + krb5Path + " /etc"});
            System.out.println("krb5 move command is " + krb5Command);
            krb5Exec.waitFor();
            if (krb5Exec.exitValue() != 0) {
                String exception = IOUtils.toString(krb5Exec.getErrorStream());
                throw new Exception(exception);
            }
        }

        //thriftserver 是nohup 启动
        String startThriftCmd = "export HADOOP_CONF_DIR=" + hadoopConfPath + "&&sh start-thrift.sh";
        System.out.println("start thrift server is +" + startThriftCmd);
        System.out.println("start thrift server start to running");
        run.exec(new String[]{"/bin/sh", "-c", startThriftCmd});
    }

    public static void kill(String applicationName, Properties prop) throws Exception {
        YarnClient client = ClusterUtils.getYarnClient(prop);
        ClusterUtils.killApplication(client, applicationName);
        int port = Integer.parseInt(prop.getProperty("jdbc.port"));
        killPid(port);
    }

    public static void killPid(int port) throws Exception {
        Runtime run = Runtime.getRuntime();
        String command = "netstat -ntlp | grep " + port + " | awk '{printf $7}'|cut -d/ -f1";
        Process exec = run.exec(new String[]{"/bin/sh", "-c", command});
        exec.waitFor();
        if (exec.exitValue() != 0) {
            String exception = IOUtils.toString(exec.getErrorStream());
            throw new Exception(exception);
        }
        //如果没有port的时候，等到的是""
        if ("".equals(IOUtils.toString(exec.getInputStream()))) {
            System.out.println("hahah");
        }
    }

    public static boolean checkThriftWork(Properties prop) throws Exception {

        Connection connection = null;
        String jdbcUrl = prop.getProperty("jdbcUrl");
        if ("true".equalsIgnoreCase(prop.getProperty("kerberos"))) {
            connection = JdbcUtils.getKerberosConnection(jdbcUrl, prop);
        } else {
            connection = JdbcUtils.getConnection(jdbcUrl, prop.getProperty("jdbc.user"));
        }

        if (connection == null) {
            return false;
        }else {
        }
        return true;
    }

}
*/
