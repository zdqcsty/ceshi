package com.example.ceshi.discp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.tools.DistCp;
import org.apache.hadoop.tools.DistCpOptions;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DistcpUtilKerberos {
    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
        List<Path> sourceList = new ArrayList<Path>();
        String source="hdfs://hadooptd1.novalocal:8020/test001/zzz";
        String target="hdfs://hadooptd1.novalocal:8020/test001/z01";
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
        conf.set("hadoop.security.authentication", "Kerberos");
        conf.addResource("/opt/beh/core/hadoop/etc/hadoop/hdfs-site.xml");
        conf.addResource("/opt/beh/core/hadoop/etc/hadoop/core-site.xml");
        UserGroupInformation.loginUserFromKeytab("test001@DEVTEST.BONC", "/opt/beh/metadata/key/test001.keytab");

//        UserGroupInformation UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@DEVTEST.BONC", "/opt/beh/metadata/key/test001.keytab");

/*        DistCp distCp = UGI.doAs(new PrivilegedAction<DistCp>() {
            @Override
            public DistCp run() {
                try {
                    return new DistCp(conf, options);
                } catch (Exception e) {
                }
                return null;
            }
        });*/

        DistCp distCp = new DistCp(conf, options);
        Job execute = distCp.execute();
        boolean complete = execute.isComplete();
        boolean successful = execute.isSuccessful();
        System.out.println(successful);
    }
}
