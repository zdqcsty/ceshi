/*
package com.example.ceshi.copyFsToFs;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.security.UserGroupInformation;
import sun.security.krb5.KrbException;

import javax.security.sasl.AuthenticationException;
import java.io.*;
import java.net.URI;
import java.security.PrivilegedAction;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Demo {

    public static final String ceshiBasePath="hdfs://devtestcluster/hive/warehouse/devtest.db/step_24247473242_parquet";
//    public static final String ceshiBasePath="hdfs://devtestcluster/test001/zzz";
    public static final String liangtaiBasePath="hdfs://behlt001/xunlian/zgh/copyHdfs";
    public AtomicReference<Exception> ex=new AtomicReference<>();

    public static void main(String[] args) throws Exception {
      CommandLineParser commandLineParser = new BasicParser();
        Options options = new Options();
        options.addOption("srcSouce",false,"srcSouce");
        options.addOption("dstSource",false,"dstSource");
        CommandLine line = commandLineParser.parse(options,args);
        String srcSource = line.getOptionValue("srcSource");
        String dstSource = line.getOptionValue("dstSource");

        FileSystem liangtaiFs = getLiangtaiFs();
        liangtaiFs.exists(new Path("aaa"));
        FileSystem ceshiFs = getCeshiFs();
        AtomicReference<Exception> ex = new AtomicReference<>();
        AtomicInteger cnt = new AtomicInteger();
        ExecutorService executor = null;
        boolean shutDownNow = false;

        long start = System.currentTimeMillis();
        try {
            executor = new ThreadPoolExecutor(5, 5,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(10));
            sync(ceshiFs, new Path(ceshiBasePath), liangtaiFs, new Path(liangtaiBasePath), cnt, ex, executor);
        } catch (Exception e) {
            shutDownNow = true;
            throw e;
        } finally {
            if (executor != null) {
                if (shutDownNow) {
                    executor.shutdownNow();
                } else {
                    executor.shutdown();
                }
                executor.awaitTermination(7200, TimeUnit.SECONDS);
            }
        }
        if (cnt.get() != 0) {
            throw new Exception();
        }
        long end = System.currentTimeMillis();
        System.out.println("拷贝数据耗费时间------"+(end-start)/1000);
        System.out.println("success");
    }


    public static FileSystem getCeshiFs() throws Exception {
*/
/*        Configuration ceshiConf=new Configuration();
        ceshiConf.addResource("ceshi/core-site.xml");
        ceshiConf.addResource("ceshi/hdfs-site.xml");
        String ceshiUri="hdfs://devtestcluster";
        String ceshiUser="test001@DEVTEST.BONC";
        String ceshikrb5ConfPath="E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf";
        String ceshiKeytabPath="E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.ketab";
        return  getFileSystemKerberos(ceshiConf, ceshiUri, ceshiUser, ceshikrb5ConfPath, ceshiKeytabPath);*//*

        Configuration ceshiConf=new Configuration();
        ceshiConf.addResource(new Path("./ceshi/core-site.xml"));
        ceshiConf.addResource(new Path("./ceshi/hdfs-site.xml"));
        String ceshiUri="hdfs://devtestcluster";
        String ceshiUser="test001@DEVTEST.BONC";
        String ceshikrb5ConfPath="./ceshi/krb5.conf";
        String ceshiKeytabPath="./ceshi/test001.keytab";
        return  getFileSystemKerberos(ceshiConf, ceshiUri, ceshiUser, ceshikrb5ConfPath, ceshiKeytabPath);
    }

    public static FileSystem getLiangtaiFs() throws Exception {
*/
/*        Configuration liangtaiConf=new Configuration();
        liangtaiConf.addResource("liangtai/core-site.xml");
        liangtaiConf.addResource("liangtai/hdfs-site.xml");
        String liangtaiUri="hdfs://behlt001";
        String liangtaiUser="xunlian@LIANGTAI.BONC";
        String liangtaikrb5ConfPath="E:\\study_workSpace\\ceshi\\src\\main\\resources\\liangtai\\krb5.conf";
        String liangtaiKeytabPath="E:\\study_workSpace\\ceshi\\src\\main\\resources\\liangtai\\xunlian.keytab";
        return  getFileSystemKerberos(liangtaiConf, liangtaiUri, liangtaiUser, liangtaikrb5ConfPath, liangtaiKeytabPath);*//*

        Configuration liangtaiConf=new Configuration();
        liangtaiConf.addResource(new Path("./liangtai/core-site.xml"));
        liangtaiConf.addResource(new Path("./liangtai/hdfs-site.xml"));
        String liangtaiUri="hdfs://behlt001";
        String liangtaiUser="xunlian@LIANGTAI.BONC";
        String liangtaikrb5ConfPath="./liangtai/krb5.conf";
        String liangtaiKeytabPath="./liangtai/xunlian.keytab";
        return  getFileSystemKerberos(liangtaiConf, liangtaiUri, liangtaiUser, liangtaikrb5ConfPath, liangtaiKeytabPath);
    }


    public static void sync(FileSystem srcfs, Path srcBasePath, FileSystem dstfs, Path dstBasePath, AtomicInteger cnt, AtomicReference<Exception> ex, Executor executor) throws Exception {
        RemoteIterator<LocatedFileStatus> statusIterator = srcfs.listLocatedStatus(srcBasePath);
        while (statusIterator.hasNext()) {
            LocatedFileStatus status = statusIterator.next();
            Path sp = status.getPath();
            Path dp = new Path(dstBasePath, sp.getName());

            if (status.isDirectory()) {
                dstfs.mkdirs(dp);
                sync(srcfs, sp, dstfs, dp, cnt, ex, executor);
            } else {
                cnt.incrementAndGet();
                CopyFileTask copyFileTask = new CopyFileTask(srcfs, sp, dstfs, dp, cnt, ex);
                executor.execute(copyFileTask);
                if (ex.get() != null) {
                    throw ex.get();
                }
            }
        }
    }

    static FileSystem getFileSystemKerberos(final Configuration conf, String hdfsUri, String user,
                                            String krb5ConfPath, String keytabPath)
            throws IOException, KrbException {

        AtomicReference<IOException> exception = new AtomicReference<>();
        // conf.set("dfs.client.use.datanode.hostname", "true");
        UserGroupInformation ugi = getUGI(conf, krb5ConfPath, user, keytabPath);
        FileSystem fs = ugi.doAs(new PrivilegedAction<FileSystem>() {
            @Override
            public FileSystem run() {
                try {
                    return FileSystem.newInstance(URI.create(hdfsUri),conf);
                } catch (IOException e) {
                    exception.set(e);
                }
                return null;
            }
        });

        if (fs == null) {
            String message = "failed to get hadoop file system for " + hdfsUri + ", user: " + user + ".";
            if (exception.get() != null) {
                Exception ex = exception.get();
                throw new AuthenticationException(message + " " + ex.getMessage(), ex);
            } else {
                throw new AuthenticationException(message);
            }
        }
        return fs;
    }

    private static void setKerberosConfiguration(Configuration conf) {
        conf.set("hadoop.security.authentication", "Kerberos");
        conf.set("hadoop.security.auth_to_local", "RULE:[1:$1]\nRULE:[2:$1]");
        conf.set("dfs.datanode.use.datanode.hostname", "false");
        conf.set("dfs.client.use.datanode.hostname", "true");
    }

    private static synchronized UserGroupInformation getUGI(Configuration conf,
                                                            String krb5ConfPath,
                                                            String user,
                                                            String keytabPath) throws KrbException, IOException {
        setKerberosConfiguration(conf);
        System.out.println(krb5ConfPath);
        System.setProperty("java.security.krb5.conf", krb5ConfPath);
        // System.setProperty("sun.security.krb5.debug", "true");
        sun.security.krb5.Config.refresh();
        UserGroupInformation.setConfiguration(conf);
        return UserGroupInformation.loginUserFromKeytabAndReturnUGI(user, keytabPath);
    }


}
*/
