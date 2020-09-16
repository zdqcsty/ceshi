package com.example.ceshi.thrift;

import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorldService.Iface {

    public HelloWorldImpl() {
    }

    @Override
    public String sayHello(String username) throws TException {
        return "Hi," + username + " welcome to thrift demo world";
    }

}