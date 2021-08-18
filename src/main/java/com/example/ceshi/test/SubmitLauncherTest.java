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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SubmitLauncherTest {

    private static Logger LOG = org.slf4j.LoggerFactory.getLogger(com.example.ceshi.test.SubmitLauncherTest.class);


    public static void main(String[] args) {
        try {
            submitJob();
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
    }

    //组装一个基础的map
    public Map<String, String> generateBaseConf() {
        String spark_home = "/opt/beh/core/spark";
        String java_home = "/opt/beh/core/jdk";
        String hadoop_config_dir = "/opt/beh/core/hadoop/etc/hadoop";
        String mainclass = "";
        String sparkModel = "client";
        String sparkMaster = "yarn";
        String spark_executor_memory = "6g";
        String spark_driver_memory = "2g";
        String spark_executor_cores = "2";
        String appResource = "jar的路径";
        String queueName = "队列名";

        //就组成一个大的方法  采用动态资源以及别的的形式  然后进行支持
        //在kerberos 层面进行区分
        //队列这块怎么配置?
        //租户这块？  这要是能配置文件生成的就弄在一起  固定的东西
        //还有一种是 local 模式区别   这种需要识别目标文件的大小

        //下午先把目标文件这块处理下吧  就是目标文件小的时候local模式  看看时间快慢

        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("spark_home", spark_home);
        baseMap.put("JAVA_HOME", java_home);
        baseMap.put("HADOOP_CONF_DIR", hadoop_config_dir);


        return new HashMap<String, String>();
    }

    public static String submitJob() throws Exception {

        //线程暂时阻塞，（main线程和startApplication是异步执行）
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        HashMap<String, String> map = new HashMap<>();
        map.put("HADOOP_CONF_DIR", "/opt/beh/core/hadoop/etc/hadoop");
        map.put("JAVA_HOME", "/opt/beh/core/jdk");

        SparkLauncher launcher = new SparkLauncher(map);

        launcher.setAppResource("hdfs:///user/zgh/ceshi.py");

        PrintWriter writer=new PrintWriter(new File("para.txt"));
        writer.println("select * from test.demoaaa");

        writer.close();

        Thread.sleep(10000);

        launcher.setMaster("client");
        launcher.setSparkHome("/opt/beh/core/spark");
        launcher.addPyFile("hdfs:///user/zgh/ceshi.py");
        List<String> args = new ArrayList<String>();
//        args.add("--sql=" + "select * from test.trino_test limit 3");

//        String sql="";
//        for (int i=0;i<10000;i++){
//             sql=sql+"cdjacdjabcdabcjdakc";
//        }

//        args.add("--sql=" + sql );
//        args.add("--output=" + "/user/zgh/loiuyhj");
//        args.add("--headers=" + "a,b,c,v,e,n");
/*        args.add("--source=" + "/user/zgh/aaa" );
        args.add("--target=" + "/user/zgh/cdahcdbjba");*/
        for (String arg : args) {
            launcher.addAppArgs(arg);
        }
//        String s = IOUtils.toString(new FileReader("/home/hadoop/zgh/demoaaa.txt"));
//
//        JSONObject json = JSONObject.parseObject(s);
//
//        String args1 = json.get("args").toString();
//
//        JSONArray argslist = JSONArray.parseArray(args1);
//
//        for (int i = 0; i < argslist.size(); i++) {
//            launcher.addAppArgs(argslist.getString(i));
//        }

        //local模式可行
        launcher.setMaster("yarn");
        launcher.setDeployMode("cluster");
        launcher.addFile("para.txt");
//        launcher.setPropertiesFile("para.txt");
/*        launcher.setConf(SparkLauncher.EXECUTOR_MEMORY, "4g");
        launcher.setConf(SparkLauncher.EXECUTOR_CORES, "2");*/
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

    public static void getTablePath(String tableName) {

        Connection connection = JdbcTemplate.getConnection();
        try {
            ResultSet resultSet = connection.prepareStatement("desc formatted " + tableName).executeQuery();
            connection.prepareStatement("desc formatted " + tableName).executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}