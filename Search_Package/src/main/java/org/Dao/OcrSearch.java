package org.Dao;


import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.util.Map;

public class OcrSearch {
    private static Logger logger = Logger.getLogger(OcrSearch.class);
    public String ocrSear(String data){
        String state = null;
        Map map = (Map) JSON.parse(data);

        String uuid = String.valueOf(map.get("uuid"));

        String expcode="YTO";
        String expNo="804058033410577922";
        CommonSearch commonSearch = new CommonSearch();
        state = commonSearch.commonSearch(uuid,expcode,expNo);

        logger.info("state------------"+state);


        return state;
    }

//    public static void main(String[] args) {
//        String data = "{" +
//                "\t\"uuid\":\"dfghjkl\"" +
//                "}";
//        OcrSearch ocrSearch = new OcrSearch();
//        ocrSearch.ocrSear(data);
//    }
}
