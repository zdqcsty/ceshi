package com.example.ceshi.byjar;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class Test1 {

    public static void main(String[] args) throws InterruptedException, IOException {

//        System.setProperty("java.security.krb5.conf","krb5.conf");
        HashMap<String, String> envParams = new HashMap<>();
        envParams.put("HADOOP_CONF_DIR", "/opt/beh/core/hadoop/etc/hadoop");
        envParams.put("SPARK_HOME", "/opt/beh/core/spark");
//        envParams.put("HADOOP_USER_NAME","hadoop");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SparkLauncher launcher = new SparkLauncher(envParams);
        launcher.setAppResource("/home/hadoop/zgh/sparklearn-0.0.1-SNAPSHOT.jar");
        // 设置spark driver主类，即appJar的主类
        launcher.setMainClass("com.example.sparklearn.Test");
        // 添加传递给spark driver mian方法的参数
        launcher.addAppArgs("devtest.vbapfeed7f1f2deb4fa08f8ea7d1700162f7_parquet", "hdfs:///user/zgh/demo");
        // 设置该spark application的master
        launcher.setMaster("yarn"); // 在yarn-cluster上启动，也可以再local[*]上
        // 关闭sparksubmit的详细报告
        launcher.setVerbose(false);
        launcher.setConf("spark.yarn.queue", "test001");
        // 设置用于执行appJar的spark集群分配的driver、executor内存等参数
//        launcher.setConf(SparkLauncher.DRIVER_MEMORY, "2g");
        launcher.setConf(SparkLauncher.EXECUTOR_MEMORY, "2g");
        launcher.setConf(SparkLauncher.EXECUTOR_CORES, "1");
        launcher.setDeployMode("client");
        launcher.addSparkArg("--keytab", "/opt/beh/metadata/key/super.keytab"); //此value为kerberos根据用户生成的公钥
        launcher.addSparkArg("--principal", "hadoop@DEVTEST.BONC");//此value为生成公钥时 使用的用户名
        // 启动执行该application
        SparkAppHandle sparkAppHandle = launcher.startApplication(new SparkAppHandle.Listener() {
            @Override
            public void stateChanged(SparkAppHandle handle) {
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
        System.out.println("The task is finished!");

    }
}
