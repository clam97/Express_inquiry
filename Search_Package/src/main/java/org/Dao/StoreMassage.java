package org.Dao;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class StoreMassage {//将寄件信息存储到redis里
    private static Logger logger = Logger.getLogger(StoreMassage.class);
    public static String getMessage(String date){
        logger.info("-------------匹配接口 storemassage 成功！已进入方法-------------------");
        String state;
        Map map = (Map) JSON.parse(date);
        Map<String,Integer> res = new HashMap<>();
        Gson gson = new Gson();
        Jedis jedis = new Jedis();


        String uuid = String.valueOf(map.get("uuid"));
        String name = String.valueOf(map.get("name"));
        String number = String.valueOf(map.get("number"));
        String finallocal = String.valueOf(map.get("finallocal"));
        String firstlocal = String.valueOf(map.get("firstlocal"));

        logger.info("接收到的uuid为："+uuid);
        logger.info("接收到的name为："+name);
        logger.info("接收到的number为："+number);
        logger.info("接收到的finallocal为："+finallocal);
        logger.info("接收到的firstlocal为："+firstlocal);



        String massage = "姓名:"+name+" "+"联系方式："+number+" "+"寄件地址："+firstlocal+" "+"收件人地址："+finallocal;
        jedis.set(uuid,massage);
        logger.info("message----------------"+massage);



        res.put("state",1);
        state = gson.toJson(res);
        logger.info("返回的结果为state"+state);

        return state;
    }
}
