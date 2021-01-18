package com.example.ceshi.sh;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.YarnClient;

import java.io.FileReader;
import java.security.PrivilegedAction;
import java.util.*;

public class ClusterUtils {

    public static Map<String, Integer> getKerberosQueueMemoryAndCores(Properties prop, String queueName) throws Exception {
        System.setProperty("java.security.krb5.conf", prop.getProperty("kerberos.krb5.path"));
        Configuration configuration = new Configuration();
        configuration.addResource(new Path(prop.getProperty("hadoop.conf.dir"), "yarn-site.xml"));
        configuration.addResource(new Path(prop.getProperty("hadoop.conf.dir"), "mapred-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        //这里keytab也是需要用绝对路径的
        System.out.println(prop);
        UserGroupInformation UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI(prop.getProperty("kerberos.keytab.priciple"), prop.getProperty("kerberos.keytab.path"));

        YarnClient client = UGI.doAs(new PrivilegedAction<YarnClient>() {
            @Override
            public YarnClient run() {
                return getYarnClient(prop);
            }
        });

        Map<String, Integer> queueMap = getMemoryAndCoresByClient(client, queueName);
        return queueMap;
    }

    public static Map<String, Integer> getQueueMemoryAndCores(Properties prop, String queueName) throws Exception {
        YarnClient client = getYarnClient(prop);
        Map<String, Integer> queueMap = getMemoryAndCoresByClient(client, queueName);
        return queueMap;
    }

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileReader("thrift.properties"));
//        final YarnClient yarnClient = getYarnClient(prop);
        Map<String, Integer> test001 = getKerberosQueueMemoryAndCores(prop, "test001");
        System.out.println(test001);
    }

    public static Map<String, Integer> getMemoryAndCoresByClient(YarnClient client, String queueName) throws Exception {
        //查看每个队列的占比
        QueueInfo queue = client.getQueueInfo(queueName);
        float capacity = queue.getCapacity();

        //total memory
        List<NodeReport> nodeReportList = client.getNodeReports();
        int totalMemory = 0;
        int totalCores = 0;
        for (NodeReport node : nodeReportList) {
            Resource resource = node.getCapability();
            totalCores = totalCores + resource.getVirtualCores();
            totalMemory = totalMemory + (int) resource.getMemorySize();
        }

        //queue memory
        int queueMemory = (int) ((totalMemory * capacity) / 1024);
        int queueCore = (int) (totalCores * capacity);

        Configuration conf = client.getConfig();
        int maxContainerMemory;
        int maxContainerCore;
        if (conf.get("yarn.scheduler.maximum-allocation-mb") == null) {
            maxContainerMemory = 8192;
        } else {
            maxContainerMemory = Integer.parseInt(conf.get("yarn.scheduler.maximum-allocation-mb"));
        }

        if (conf.get("yarn.scheduler.maximum-allocation-vcores") == null) {
            maxContainerCore = 4;
        } else {
            maxContainerCore = Integer.parseInt(conf.get("yarn.scheduler.maximum-allocation-vcores"));
        }

        Map<String, Integer> map = new HashMap();
        map.put("queueMemory", queueMemory);
        map.put("queueCore", queueCore);
        map.put("maxContainerMemory", maxContainerMemory);
        map.put("maxContainerCore", maxContainerCore);

        client.close();
        return map;
    }

    public static YarnClient getYarnClient(Properties prop) {
        YarnClient yarnClient = YarnClient.createYarnClient();
        Configuration conf = new Configuration();
        conf.addResource(new Path("/opt/beh/core/hadoop/etc/hadoop/yarn-site.xml"));
        conf.addResource(new Path("/opt/beh/core/hadoop/etc/hadoop/mapred-site.xml"));
/*        conf.addResource(new Path("ceshi/yarn-site.xml"));
        conf.addResource(new Path("ceshi/mapred-site.xml"));*/
//        conf.addResource(new Path(prop.getProperty("yarnsite.path")));
//        conf.addResource(new Path(prop.getProperty("mapredsite.path")));
        yarnClient.init(conf);
        yarnClient.start();
        return yarnClient;
    }

    /**
     * 根据applicationName 获取application 并kill
     *
     * @param client
     * @param applicationName
     * @throws Exception
     */
    public static void killApplication(YarnClient client, String applicationName) throws Exception {

        ApplicationId applicationId = null;
        List<ApplicationReport> applications = client.getApplications(EnumSet.of(YarnApplicationState.RUNNING));

        for (ApplicationReport application : applications) {
            if (applicationName.equals(application.getName())) {
                applicationId = application.getApplicationId();
                break;
            }
        }
        if (applicationId == null) {
            return;
        }

        client.killApplication(applicationId);
    }
}
