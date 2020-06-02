package com.example.ceshi.test;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test6 {

    public static void main(String[] args) {



        SparkSession sparkSession = SparkSession.builder().enableHiveSupport().getOrCreate();

        sparkSession.sql("select * from hebing.vbapfdb527f3e326488f92051cca278a49ad_parquet").show();


        sparkSession.cloneSession();

    }

}
