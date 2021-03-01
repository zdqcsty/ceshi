package com.example.ceshi.test;

import java.io.*;
import java.net.*;

class SocketThreadClient extends Thread {
    public static int count = 0;

    public SocketThreadClient(InetAddress addr) {
        count++;
        BufferedReader in = null;
        PrintWriter out = null;
        Socket sk = null;
        try {

            sk = new Socket(addr, 8000);
            InputStreamReader isr;
            isr = new InputStreamReader(sk.getInputStream());
            in = new BufferedReader(isr);

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);

            System.out.println("count:" + count);
            out.println("Hello");
            System.out.println(in.readLine());
            out.println("BYE");
            System.out.println(in.readLine());

        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            out.println("END");

            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                if (sk != null)
                    sk.close();
            } catch (IOException e) {
            }
        }
    }
}

public class SocketClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress addr = InetAddress.getByName(null);
        for (int i = 0; i < 10; i++)
            new SocketThreadClient(addr);
        Thread.currentThread().sleep(1000);
    }
}