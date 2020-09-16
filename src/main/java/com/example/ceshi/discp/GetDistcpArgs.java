package com.example.ceshi.discp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.tools.DistCp;
import org.apache.hadoop.tools.DistCpOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：liliang
 * 日期：2020/8/26
 */
public class GetDistcpArgs {
    public static void main(String[] args) throws Exception {
        String src_url  ="jdbc:hive2://hadoop04.novalocal:2188,hadoop02.novalocal:2188,hadoop08.novalocal:2188/hive;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2_zk";
        String dest_url="jdbc:hive2://hadoop04.novalocal:2188,hadoop02.novalocal:2188,hadoop08.novalocal:2188/hive;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2_zk";
        String tableName="vbapffba9dca5df44dc088cc151ee4e69f91_parquet";
        String user="hadoop";
        String loadPath="hdfs://nameservice1/tmp/logs";
        Map map = getDistcpArgs(src_url, tableName, user);
        String SQL = String.valueOf(map.get("createTableSQL"));
        String src_location=String.valueOf(map.get("src_location"));
        System.out.println("原表的存放路径是："+src_location);
        System.out.println("获取的原表建表语句是："+SQL);
        //Boolean aBoolean = putDistcpArgs(dest_url, "hdfs://beh001/hive/warehouse/hive.db/vbapffba9dca5df44dc088cc151ee4e69f91_pa2", SQL);
        List<Path> sourceList = new ArrayList<Path>();
        sourceList.add(new Path(src_location));
        DistCpOptions options = new DistCpOptions(sourceList, new Path(loadPath));
        options.setSyncFolder(false); //不需要同步
        options.setDeleteMissing(false);
        options.setSkipCRC(false); //同副本复制需要校验filechecksums
        options.setIgnoreFailures(false);//不能忽略map错误
        options.setOverwrite(true); //核心数据需要覆盖目标目录已经存在的文件
        options.setMaxMaps(100);
        options.setMapBandwidth(10);
        /*options.preserve(DistCpOptions.FileAttribute.USER);
        options.preserve(DistCpOptions.FileAttribute.GROUP);
        options.preserve(DistCpOptions.FileAttribute.PERMISSION);
        options.preserve(DistCpOptions.FileAttribute.REPLICATION);
        options.preserve(DistCpOptions.FileAttribute.BLOCKSIZE); //必须确保块大小相同，否则会导致filechecksum不一致*/
        options.setBlocking(true);
        Configuration configuration = new Configuration();
        //configuration.addResource(new Path("C:\\Users\\Leeliang\\Desktop\\transformData\\src\\main\\resources\\core-site.xml"));
        configuration.addResource(new Path("C:\\Users\\Leeliang\\Desktop\\transformData\\src\\main\\resources\\hdfs-site.xml"));
        DistCp distCp = new DistCp(configuration, options);
        Job execute = distCp.execute();
        boolean complete = execute.isComplete();
        boolean successful = execute.isSuccessful();
        System.out.println(successful);
    }

    public static Map getDistcpArgs(String jdbcURL, String tableName,String user) throws SQLException {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(jdbcURL, "root", "");
        Statement stmt = conn.createStatement();
        Statement stmt1 = conn.createStatement();
        String sql = "desc extended  "+tableName;
        String sql2="show create table  "+tableName;
        ResultSet res = stmt1.executeQuery(sql);
        ResultSet resultSet = stmt.executeQuery(sql2);
        String createTableSQL=null;
        while (resultSet.next()){
            createTableSQL= resultSet.getString(1);
        }
        Map hashMap = new HashMap();
        while (res.next()) {
            hashMap.put(res.getString(1),res.getString(2));
        }
        String type = String.valueOf(hashMap.get("Type"));
        Map map = new HashMap();
        if ("EXTERNAL".equals(type)){
            String location = String.valueOf(hashMap.get("Location"));
            String[] split = createTableSQL.split("LOCATION");
            createTableSQL = split[0].replace("CREATE EXTERNAL TABLE", "CREATE  TABLE");
            map.put("createTableSQL",createTableSQL);
            map.put("src_location",location);
            return map;
        }
        String location = String.valueOf(hashMap.get("Location"));
        System.out.println("location================"+location+"type=============="+type);
        res.close();
        stmt.close();
        conn.close();
        map.put("createTableSQL",createTableSQL);
        map.put("src_location",location);
        return map;
    }

    public static Boolean putDistcpArgs(String jdbcURL,String loadPath ,String sql) throws SQLException {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(jdbcURL, "root", "");
        Statement stmt = conn.createStatement();
        String createTableSQL= sql +"  location '"+loadPath+" '";
        System.out.println("新建表的语句是: "+createTableSQL);
        boolean execute = stmt.execute(createTableSQL);
        return execute;
    }
}
