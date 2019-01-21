package org.Dao;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.Util.MyEmailUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;


public class CallMe {//联系快递员并将用户的寄件地址发送给对应的快递员
    private static Logger logger = Logger.getLogger(CallMe.class);
    public static String call(String date){
        logger.info("-------------匹配接口 callme 成功！已进入方法-------------------");
        String state;
        Map map = (Map) JSON.parse(date);
        Map res = new HashMap();
        Gson gson = new Gson();
        Jedis jedis = new Jedis();

        String uuid =  String.valueOf(map.get("uuid"));
        String pname =  String.valueOf(map.get("pname"));
        String pemail =  String.valueOf(map.get("pemail"));

        logger.info("uuid--------"+uuid);
        logger.info("poster--------"+pname);
        logger.info("pemail--------"+pemail);

        String message = jedis.get(uuid);
        String code =pname+":"+"您有新的订单,请及时查看\n"+message;
        MyEmailUtil.doSend(pemail,code);

        res.put("state",1);
        state = gson.toJson(res);
        logger.info("state---------"+state);
        return state;
    }

}
