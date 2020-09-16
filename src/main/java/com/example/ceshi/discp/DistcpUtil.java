package com.example.ceshi.discp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.tools.DistCp;
import org.apache.hadoop.tools.DistCpOptions;

import java.util.ArrayList;
import java.util.List;

public class DistcpUtil {
    public static void main(String[] args) throws Exception {
/*        System.setProperty("hadoop.home.dir", "E:\\bigdata\\hadoop-2.9.1");
        System.load("E:\\bigdata\\hadoop-2.9.1\\bin\\hadoop.dll");*/



        List<Path> sourceList = new ArrayList<Path>();
        String source="hdfs://hebing2.novalocal:8020/user/zgh/aaa";
        String target="hdfs://hebing2.novalocal:8020/user/zgh/ccc";
        sourceList.add(new Path(source));

        DistCpOptions options=new DistCpOptions(sourceList, new Path(target));

//        DistCpOptions options = new DistCpOptions(sourceList, new Path(target));
     /*   options.setSyncFolder(false); //不需要同步
        options.setDeleteMissing(false);
        options.setSkipCRC(false); //同副本复制需要校验filechecksums
        options.setIgnoreFailures(false);//不能忽略map错误
        options.setOverwrite(true); //核心数据需要覆盖目标目录已经存在的文件
        options.setMaxMaps(100);
        options.setMapBandwidth(10);
        options.setBlocking(true);*/
        Configuration conf = new Configuration();
/*        conf.addResource("/opt/beh/core/hadoop/etc/hadoop/hdfs-site.xml");
        conf.addResource("/opt/beh/core/hadoop/etc/hadoop/core-site.xml");
        conf.addResource("/opt/beh/core/hadoop/etc/hadoop/mapred-site.xml");
        conf.addResource("/opt/beh/core/hadoop/etc/hadoop/yarn-site.xml");*/

/*        conf.addResource("E:\\study_workSpace\\ceshi\\src\\main\\resources\\hebing\\core-site.xml");
        conf.addResource("E:\\study_workSpace\\ceshi\\src\\main\\resources\\hebing\\hdfs-site.xml");
        conf.addResource("E:\\study_workSpace\\ceshi\\src\\main\\resources\\hebing\\mapred-site.xml");
        conf.addResource("E:\\study_workSpace\\ceshi\\src\\main\\resources\\hebing\\yarn-site.xml");*/
/*        conf.set("dfs.client.use.datanode.hostname", "true");*/

        DistCp distCp = new DistCp(conf, options);
        Job execute = distCp.execute();
        boolean complete = execute.isComplete();
        boolean successful = execute.isSuccessful();
        System.out.println(successful);
    }
}
