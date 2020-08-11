package com.example.ceshi.test;

import cn.binarywang.tools.generator.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.swing.table.DefaultTableCellRenderer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.StringJoiner;

public class ProducerData {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        //身份证号码
        ChineseIDCardNumberGenerator cidcng = (ChineseIDCardNumberGenerator) ChineseIDCardNumberGenerator.getInstance();
        //中文姓名
        ChineseNameGenerator cng = ChineseNameGenerator.getInstance();
        //英文姓名
        EnglishNameGenerator eng = EnglishNameGenerator.getInstance();
        //手机号
        ChineseMobileNumberGenerator cmng = ChineseMobileNumberGenerator.getInstance();
        //电子邮箱
        EmailAddressGenerator eag = (EmailAddressGenerator) EmailAddressGenerator.getInstance();
        //居住地址
        ChineseAddressGenerator cag = (ChineseAddressGenerator) ChineseAddressGenerator.getInstance();

        Random random=new Random();

        Properties props = new Properties();
        props.put("bootstrap.servers", "10.130.7.208:9092");
        props.put("acks", "0");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //生产者发送消息
        String topic = "test";
        org.apache.kafka.clients.producer.Producer<String, String> procuder = new KafkaProducer<String, String>(props);
        PrintWriter pw = new PrintWriter("E:\\data.csv");
        for (int i = 1; i < 10000000; i++) {
            //键入一列日期时间
            StringJoiner sj = new StringJoiner(",");
            sj.add(String.valueOf(random.nextInt(1)));
            sj.add(cng.generate());
            sj.add(eng.generate());
            sj.add(cmng.generate());
            sj.add(eag.generate());
            sj.add(String.valueOf(new Date().getTime()));
            Thread.sleep(1000);
            procuder.send(new ProducerRecord<String, String>(topic,"demo", sj.toString()));
            System.out.println(sj.toString());
        }
    }
}
