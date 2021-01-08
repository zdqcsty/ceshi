package com.example.ceshi.test;

import com.example.ceshi.jdbc.JdbcTemplate;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SubmitPython {


    public static void main(String[] args) {
        try {
            submitJob();
        } catch (Exception e) {
            e.printStackTrace();
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

        launcher.setSparkHome("/opt/beh/core/spark");
        launcher.addPyFile("hdfs:///user/zgh/ceshi.py");
        List<String> args = new ArrayList<String>();
//        args.add("--sql=" + "select `id` from test.demoaaa where (`id`=1)" );
        args.add("--sql=" + "select acct_day, projectNo, project_name, proj_type, proj_type_desc, proj_status, proj_status_desc, processInstanceStatus, processInstanceStatus_desc, projectManagerId, projectManagerName, customerManagerId, customerManagerName, proj_org_name, proj_trip_name, budget_baoxiao_cost_year from stress.step_step_25379480439 where (`proj_type_desc` in('商机','职能','研发') and `proj_status_desc`='进行中' and   `processInstanceStatus_desc`='已完成' and `budget_baoxiao_cost_year`=0) limit 10" );
//        args.add("--output=" + "/user/zgh/loiuyhj");
//        args.add("--headers=" + "a,b,c,v,e,n");
/*        args.add("--source=" + "/user/zgh/aaa" );
        args.add("--target=" + "/user/zgh/cdahcdbjba");*/
        for (String arg: args) {
            launcher.addAppArgs(arg);
        }
        //local模式可行
        launcher.setMaster("yarn");
        launcher.setDeployMode("client");
/*        launcher.setConf(SparkLauncher.EXECUTOR_MEMORY, "4g");
        launcher.setConf(SparkLauncher.EXECUTOR_CORES, "2");*/
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
