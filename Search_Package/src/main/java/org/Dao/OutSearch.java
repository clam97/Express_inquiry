package org.Dao;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.Util.Codecase;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class OutSearch {
    private static Logger logger = Logger.getLogger(OutSearch.class);
    public static String outSearch(String date){
        logger.info("-------------匹配接口 outSearch 成功！已进入方法-------------------");

        String state;

        Map map = (Map) JSON.parse(date);

        String uuid = String.valueOf(map.get("uuid"));
        String expCode = String.valueOf(map.get("expCode"));//快递公司
        String expNo = String.valueOf(map.get("expNo"));//快递单号

        logger.info("uuid=========="+uuid);
        logger.info("expCode=========="+expCode);
        logger.info("expNo=========="+expNo);

        Codecase codecase = new Codecase();
        String expcode = codecase.code(expCode);

        CommonSearch commonSearch = new CommonSearch();
        state = commonSearch.commonSearch(uuid,expcode,expNo);

        logger.info("state------------"+state);
        return state;

    }

//    public static void main(String[] args) {
//        String data="{\n" +
//                "\t\" uuid\": \"7895232\",\n" +
//                "\t\"expCode\": \"YTO\",\n" +
//                "\t\"expNo\": \"804058033410577922\"\n" +
//                "}";
//       OutSearch.outSearch(data);
//    }

}
