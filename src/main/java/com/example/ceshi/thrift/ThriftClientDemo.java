package com.example.ceshi.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftClientDemo {

    public static void main(String[] args) {
        try {
            TTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 8191, 5000));
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);

            HelloWorldService.Client client = new HelloWorldService.Client(protocol);

            transport.open();

            String string = client.sayHello("Neo");

            System.out.println(string);

            transport.close();

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}