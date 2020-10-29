package com.example.ceshi.test;

import org.apache.commons.cli.*;

import java.util.UUID;

public class Demo {

    public static void main(String[] args) throws ParseException {

        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("krb5ConfPath",false,"krb5.conf");
        options.addOption("user",false,"user");
        options.addOption("keytabPath",false,"keytabPath");
        CommandLine cl = parser.parse(options, args);
        final String krb5ConfPath = cl.getOptionValue("krb5ConfPath");
        final String user = cl.getOptionValue("user");
        final String keytabPath = cl.getOptionValue("keytabPath");

        System.out.println("krb5ConfPath--------"+krb5ConfPath);
        System.out.println("user--------"+ UUID.randomUUID());
        System.out.println("keytabPath--------"+keytabPath);
    }
}
