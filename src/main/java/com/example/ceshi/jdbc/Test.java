package com.example.ceshi.jdbc;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.QueueInfo;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hive.jdbc.HiveStatement;

import java.sql.*;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;


public class Test {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    //    private static String CONNECTION_URL = "jdbc:hive2://liangtai1.novalocal:2188,liangtai2.novalocal:2188,liangtai3.novalocal:2188/liangtai;principal=hs2/_HOST@LIANGTAI.BONC;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";
    private static String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/test";

    public static void main(String[] args) throws Exception {

        String applicationName = UUID.randomUUID().toString();

        ApplicationId applicationId = null;

        new Thread(new ExecutorRunnable(applicationName)).start();

        final YarnClient yarnClient = getYarnClient();

        boolean flag = true;

        final long l = System.currentTimeMillis();
        while (flag) {
            List<ApplicationReport> applications = null;
            try {
                applications = yarnClient.getApplications(EnumSet.of(YarnApplicationState.RUNNING, YarnApplicationState.ACCEPTED, YarnApplicationState.NEW, YarnApplicationState.NEW_SAVING, YarnApplicationState.SUBMITTED));
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (ApplicationReport application : applications) {
                if (applicationName.equals(application.getName())) {
                    applicationId = application.getApplicationId();

                    System.out.println(applicationId.toString());

                    flag = false;
                    break;
                }
            }
            Thread.sleep(1000);

            System.out.println((System.currentTimeMillis() - l) / 1000);

            if ((System.currentTimeMillis() - l) / 1000 >= 10) {
                System.out.println("can not find app id in 10 s");
            }

        }

        while (true) {
            Thread.sleep(1000);
            ApplicationReport applicationReport = yarnClient.getApplicationReport(applicationId);
            System.out.println(applicationReport.getYarnApplicationState().toString());
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");
        } catch (SQLException throwables) {
            return null;
        }
        return connection;
    }

    public static YarnClient getYarnClient() {
        YarnClient yarnClient = YarnClient.createYarnClient();
        Configuration conf = new Configuration();
        conf.addResource(new Path("E:\\study_workSpace\\ceshi\\src\\main\\resources\\hebing\\yarn-site.xml"));
        conf.addResource(new Path("E:\\study_workSpace\\ceshi\\src\\main\\resources\\hebing\\core-site.xml"));
        conf.addResource(new Path("E:\\study_workSpace\\ceshi\\src\\main\\resources\\hebing\\hdfs-site.xml"));
        conf.set("dfs.client.use.datanode.hostname", "true");
        yarnClient.init(conf);
        yarnClient.start();
        return yarnClient;
    }


    static class LogRunnable implements Callable {

        private YarnClient yarnClient;
        private String applicationName;


        public LogRunnable(YarnClient yarnClient, String applicationName) {
            this.yarnClient = yarnClient;
            this.applicationName = applicationName;
        }

        @Override
        public Object call() throws Exception {
            List<ApplicationReport> applications = null;
            try {
                applications = yarnClient.getApplications(EnumSet.of(YarnApplicationState.RUNNING, YarnApplicationState.ACCEPTED, YarnApplicationState.NEW, YarnApplicationState.NEW_SAVING, YarnApplicationState.SUBMITTED));
            } catch (Exception e) {
                e.printStackTrace();
            }

            while (true) {
                for (ApplicationReport application : applications) {
                    System.out.println(application.getName());
                    if (applicationName.equals(application.getName())) {
                        return application.getApplicationId().toString();
                    }
                }
            }
        }
    }

    static class ExecutorRunnable implements Runnable {

        private String applicationName;

        public ExecutorRunnable(String applicationName) {
            this.applicationName = applicationName;
        }

        @Override
        public void run() {
            Connection connection = getConnection();
            Statement state = null;
            try {
                state = connection.createStatement();
                state.execute("set mapreduce.job.name=" + applicationName + "");
                state.execute("create table  democdacbda as select * from testparquet limit 10");

                state.execute("set mapreduce.job.name=;");
                state.execute("create table  democdacdacdabc as select * from testparquet limit 10");
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }

        }
    }


}