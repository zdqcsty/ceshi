package com.example.ceshi.test;

import com.example.ceshi.pool.ObjectPool;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public final class Test3 {
    static Logger LOG = org.slf4j.LoggerFactory.getLogger(Test3.class);

//    String oracleCtl="${aaaaa}"

    public static void main(String[] args) throws Exception {

//        Map<String, Object> param = new HashMap<>();
//        StringSubstitutor sub = new StringSubstitutor(param);
//
//        String result = sub.replace(oracleCtl);
//        PrintWriter pw = new PrintWriter();

        String bbb = "FIELD_TREE_ID,DISPLAY_NAME,PARENT_TREE_ID,DATA_SET_ID,DATA_SET_FIELD_ID,IS_DATA_SET,MODIFY_TIME,CREATE_TIME,CREATE_USER_ID,PATH,FIELD_CODE";
        String aaa = "create_time \"to_date(:create_time,'yyyy-MM-dd HH:mm:ss')\"";


        final String replace = bbb.replaceAll("CREATE_TIME", aaa);


        System.out.println(replace);
    }
}
