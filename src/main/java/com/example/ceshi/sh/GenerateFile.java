package com.example.ceshi.sh;

import org.apache.commons.text.StringSubstitutor;
import org.apache.tools.ant.util.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GenerateFile {

    private static final String THRIFT_SHELL = "#!/bin/bash \n" +
            "${THRIFTSERVER} --master yarn \\ \n" +
            "        --deploy-mode client \\ \n" +
            "        --queue ${QUEUE} \\ \n" +
            "        --name ${NAME} \\ \n" +
            "        --hiveconf hive.server2.thrift.min.worker.threads 20 \\ \n" +
            "        --hiveconf hive.server2.thrift.max.worker.threads 1000 \\ \n" +
            "        --conf spark.driver.memory=${DRIVERMEMORY} \\ \n" +
            "        --conf spark.driver.maxResultSize=${MAXRESULTSIZE} \\ \n" +
            "        --num-executors ${NUMEXECUTORS} \\ \n" +
            "        --conf spark.executor.memory=${EXECUTORMEMORY} \\ \n" +
            "        --conf spark.executor.cores=${EXECUTORCORES} \\ \n" +
            "        --conf spark.scheduler.mode=FAIR \\ \n" +
            "        --conf spark.hadoop.fs.hdfs.impl.disable.cache=true \\ \n" +
            "        --conf spark.kryoserializer.buffer.max=256m \\ \n" +
            "        --conf spark.sql.sources.partitionColumnTypeInference.enabled=false \\ \n" +
            "        --conf spark.shuffle.service.enabled=true \\ \n" +
            "        --conf spark.sql.auto.repartition=true \\ \n" +
            "        --conf spark.sql.adaptive.enabled=true \\ \n" +
            "        --conf spark.scheduler.allocation.file=./fairscheduler.xml \\ \n" +
            "        --conf spark.sql.thriftServer.incrementalCollect=true \\ \n" +
            "        ${KEYTAB} \n" +
            "        ${PRICIPLE}";

    private static final String FAIR_SCHEDULE = "<?xml version=\"1.0\"?>\n" +
            "\n" +
            "<allocations>\n" +
            "  <pool name=\"default\">\n" +
            "    <schedulingMode>FAIR</schedulingMode>\n" +
            "    <weight>50</weight>\n" +
            "    <minShare>${DEFAULTCORE}</minShare>\n" +
            "  </pool>\n" +
            "  <pool name=\"low\">\n" +
            "    <schedulingMode>FAIR</schedulingMode>\n" +
            "    <weight>10</weight>\n" +
            "    <minShare>${OTHERCORE}</minShare>\n" +
            "  </pool>\n" +
            "  <pool name=\"mid\">\n" +
            "    <schedulingMode>FAIR</schedulingMode>\n" +
            "    <weight>10</weight>\n" +
            "    <minShare>${OTHERCORE}</minShare>\n" +
            "  </pool>\n" +
            "  <pool name=\"high\">\n" +
            "    <schedulingMode>FAIR</schedulingMode>\n" +
            "    <weight>20</weight>\n" +
            "    <minShare>${OTHERCORE}</minShare>\n" +
            "  </pool>\n" +
            "</allocations>\n";


    public static void generateStartThriftShell(double percent, Properties prop, String applicationName) throws Exception {

        Map<String, String> paraMap = new HashMap<>();
        boolean auto = Boolean.parseBoolean(prop.getProperty("thrift.auto"));
        String queue = prop.getProperty("spark.queue");
        boolean kerberos = Boolean.parseBoolean(prop.getProperty("kerberos"));

        paraMap.put("QUEUE", queue);
        paraMap.put("NAME", applicationName);
        paraMap.put("THRIFTSERVER", prop.getProperty("thrift.start.sh.path"));

        //kerberos
        if (kerberos == true) {
            paraMap.put("KEYTAB", "--keytab " + prop.getProperty("kerberos.keytab.path"));
            paraMap.put("PRICIPLE", "--principal " + prop.getProperty("kerberos.keytab.priciple"));
        } else {
            paraMap.put("KEYTAB", "");
            paraMap.put("PRICIPLE", "");
        }

        //运行参数
        if (auto == true) {
            paraMap = generateAutoPara(prop, kerberos, queue, percent, paraMap);
        } else {
            paraMap.put("NUMEXECUTORS", prop.getProperty("spark.numExecutors"));
            paraMap.put("EXECUTORMEMORY", prop.getProperty("spark.executorMemory"));
            paraMap.put("EXECUTORCORES", prop.getProperty("spark.executorCores"));
        }

        //driver
        paraMap.put("DRIVERMEMORY", prop.getProperty("spark.driverMemory"));
        paraMap.put("MAXRESULTSIZE", prop.getProperty("spark.maxResultSize"));

        int totalCore = getTotalCore(paraMap);
        generateFairSchedulerXml(totalCore);

        File file = new File("./start_thriftserver.sh");
        if (file.exists()) {
            FileUtils.delete(file);
        }

        StringSubstitutor sub = new StringSubstitutor(paraMap);
        String replace = sub.replace(THRIFT_SHELL);
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print(replace);
        }
    }

    private static int getTotalCore(Map<String, String> paraMap) {
        int numExecutors = Integer.parseInt(paraMap.get("NUMEXECUTORS"));
        int executorCore = Integer.parseInt(paraMap.get("EXECUTORCORES"));
        return numExecutors * executorCore;
    }

    private static Map<String, String> generateAutoPara(Properties prop, boolean kerberos, String queueName, double percent, Map<String, String> map) throws Exception {
        Map<String, Integer> paraMap = null;
        if (kerberos == true) {
            paraMap = ClusterUtils.getKerberosQueueMemoryAndCores(prop, queueName);
        } else {
            paraMap = ClusterUtils.getQueueMemoryAndCores(prop, queueName);
        }
        int queueMemory = paraMap.get("queueMemory");
        int maxContainerMemory = paraMap.get("maxContainerMemory");
        int maxContainerCore = paraMap.get("maxContainerCore");

        int thriftMemory = (int) (queueMemory * percent);

        //自动分配内存和core
        int core;
        int numExecutor;
        int memory;
        if (maxContainerMemory > 12) {
            memory = 12;
            numExecutor = thriftMemory / memory;
            core = 5 > maxContainerCore ? maxContainerCore : 5;
        } else {
            memory = maxContainerMemory - 1;
            numExecutor = thriftMemory / memory;
            core = memory / 3;
        }

        map.put("EXECUTORMEMORY", String.valueOf(memory));
        map.put("NUMEXECUTORS", String.valueOf(numExecutor));
        map.put("EXECUTORCORES", String.valueOf(core));

        return map;
    }

    public static void generateFairSchedulerXml(int totalCore) throws IOException {

        int fairCore = (int) (totalCore * 0.6);
        int defaultCore = fairCore / 2;
        int otherCore = fairCore / 6;

        Map<String, Integer> paraMap = new HashMap<>();
        paraMap.put("DEFAULTCORE", defaultCore);
        paraMap.put("OTHERCORE", otherCore);

        File file = new File("./fairscheduler.xml");
        if (file.exists()) {
            FileUtils.delete(file);
        }

        StringSubstitutor sub = new StringSubstitutor(paraMap);
        String replace = sub.replace(FAIR_SCHEDULE);
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println(replace);
        }
    }

}

