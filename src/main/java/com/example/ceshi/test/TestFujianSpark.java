package com.example.ceshi.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ceshi.jdbc.JdbcTemplate;
import org.apache.commons.io.IOUtils;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class TestFujianSpark {

    private static Logger LOG = org.slf4j.LoggerFactory.getLogger(com.example.ceshi.test.SubmitLauncherTest.class);


    public static void main(String[] args) {
        try {
            submitJob();
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
    }


    public static String submitJob() throws Exception {

        Properties prop=new Properties();
        prop.load(new FileReader("test.conf"));

        final String java_home = prop.getProperty("java.home");
        final String hadoopConf = prop.getProperty("hadoop.conf");
        final String pyfile = prop.getProperty("pyfile");
        final String sparkHome = prop.getProperty("spark.home");

        final String keytabPath = prop.getProperty("keytab.path");
        final String priciple = prop.getProperty("priciple");
        final String krb5conf = prop.getProperty("krb5conf");

        final String queue = prop.getProperty("queue");

        final String python = prop.getProperty("python");


        //线程暂时阻塞，（main线程和startApplication是异步执行）
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        System.setProperty("java.security.krb5.conf", krb5conf);

        HashMap<String, String> map = new HashMap<>();
        map.put("HADOOP_CONF_DIR", hadoopConf);
        map.put("JAVA_HOME", java_home);

        SparkLauncher launcher = new SparkLauncher(map);

        launcher.setConf("spark.pyspark.driver.python", python);
        launcher.setConf("spark.pyspark.python", python);

        launcher.setAppResource(pyfile);

        launcher.setSparkHome(sparkHome);
        launcher.addPyFile(pyfile);

        launcher.setMaster("yarn");
        launcher.setDeployMode("client");
        launcher.setConf("spark.yarn.queue", queue);

        launcher.addSparkArg("--keytab", keytabPath); //此value为kerberos根据用户生成的公钥
        launcher.addSparkArg("--principal", priciple);//此value为生成公钥时 使用的用户名

        SparkAppHandle sparkAppHandle = launcher.startApplication(new SparkAppHandle.Listener() {
            @Override
            public void stateChanged(SparkAppHandle handle) {

                Map<String, String> map = new HashMap<>();

                if ("FAILED".equals(handle.getState().toString())) {
                    System.out.println("---------------");
                    map.put(handle.getAppId(), "FAILED");
                    countDownLatch.countDown();
                }
                if (handle.getState().isFinal()) {
                    countDownLatch.countDown();
                }
                System.out.println("state:" + handle.getState().toString());
            }

            @Override
            public void infoChanged(SparkAppHandle handle) {
            }
        });

        System.out.println("The task is executing, please wait ....");
        //线程等待任务结束
        countDownLatch.await();
        System.out.println("===================================");
        System.out.println(sparkAppHandle.getAppId() + "---" + sparkAppHandle.getState());
        System.out.println("===================================");
        return "aaa";
    }

}