package com.example.ceshi.pythondemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PythonFile {

    public static void main(String[] args) throws FileNotFoundException {

//        File file=new File("D:\\a.txt");
        PrintWriter pw=new PrintWriter("D:\\a.py");

        pw.println("from blazingsql import BlazingContext");
        pw.println("import dask_cudf");
        pw.println("import dask");
        pw.println("from dask.distributed import Client");
        pw.println("import time");

        pw.println("bc = BlazingContext()");
        pw.println("bc.create_table('test', '/blazingsql/testData/test8000.csv')");
        pw.println("query='''select count(*) from test'''");
        pw.println("start=time.time()");
        pw.println("result=bc.sql(query)");
        pw.println("end=time.time()");
        pw.println("eclapse=end-start");
        pw.println("print(result)");
        pw.println("print(eclapse)");

        pw.close();

    }


}
