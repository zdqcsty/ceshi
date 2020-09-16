package com.example.ceshi.test;

import org.apache.commons.cli.*;


public class Test1 {

    public static void main(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new BasicParser();

        Options options = new Options();

        options.addOption("demo","hdfs",false,"start hdfs");
        options.addOption("ceshi","yarn",false,"start yarn");

        CommandLine line = commandLineParser.parse(options,args);

        if (line.hasOption("demo")){
            System.out.println("hahah");
        }

    }

}
