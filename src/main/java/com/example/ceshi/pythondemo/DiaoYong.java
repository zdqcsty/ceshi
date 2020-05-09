package com.example.ceshi.pythondemo;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DiaoYong {

    public static void main(String[] args) throws IOException {
//
//        String result = "";
//
//        try {
//            Process process = Runtime.getRuntime().exec("python  /home/hadoop/zgh/demo.py");
//            process.waitFor();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            String str = null;
//            while((str = bufferedReader.readLine()) != null)
//            {
//                System.out.println(str);
//            }
//
//            bufferedReader.close();
//
//        } catch (IOException e) {
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        getInputStream(aaa());

        String url="http://10.130.2.132:8011/testHdfs";

        sendPost(url);

    }


    public static void getInputStream(InputStream is) throws IOException {


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }


    }

    public static InputStream aaa() {

        InputStream resourceAsStream = DiaoYong.class.getClassLoader().getResourceAsStream("demo.txt");


        return resourceAsStream;
    }

    public static String sendPost(String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

//            // 设置请求属性
//            String param = "";
//            if (paramMap != null && paramMap.size() > 0) {
//                Iterator<String> ite = paramMap.keySet().iterator();
//                while (ite.hasNext()) {
//                    String key = ite.next();// key
//                    String value = paramMap.get(key);
//                    param += key + "=" + value + "&";
//                }
//                param = param.substring(0, param.length() - 1);
//            }

            String param="sql=select * from demo";
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
