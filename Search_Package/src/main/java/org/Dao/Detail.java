package org.Dao;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.util.Map;


public class Detail {
    private static Logger logger = Logger.getLogger(Detail.class);
    public String detail(String data){
        String state = null;
        Map map = (Map) JSON.parse(data);
        String uuid = String.valueOf(map.get("uuid"));
        String expCode = String.valueOf(map.get("expCode"));//快递公司
        String expNo = String.valueOf(map.get("expNo"));

        CommonSearch commonSearch = new CommonSearch();
        state = commonSearch.commonSearch(uuid,expCode,expNo);

        logger.info("返回的state:"+state);
        return state;
    }
}
