package org.Dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.SqlDeal.InsertSearch;
import org.Util.KdniaoTrackQueryAPI;
import org.apache.log4j.Logger;
import org.pojo.Trance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommonSearch {
    private static Logger logger = Logger.getLogger(CommonSearch.class);
    public String commonSearch(String uuid,String expCode,String expNo){
        String state;
        Map res = new HashMap();
        Gson gson = new Gson();
        String message = null;
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
        try {

            // String result = api.getOrderTracesByJson("ZTO", "75110898818842");
            String result = api.getOrderTracesByJson(expCode, expNo);
            JSONObject jsonObject = JSON.parseObject(result);
            Boolean success = (Boolean) jsonObject.get("Success");
            if (success==true){
                String traceArray=jsonObject.getString("Traces");
                List<Trance> trances_list = new ArrayList<Trance>(JSONArray.parseArray(traceArray, Trance.class));
                for (Trance trance : trances_list) {
                    message="时间：" + trance.getAcceptTime()+"      "+trance.getAcceptStation()+"\n";
                    System.out.println();
                    message +=message;

                }
                res.put("state",1);
                res.put("message",message);
                InsertSearch.insertSearch(uuid,expCode,expNo);

            }else {
                System.out.println("暂无该快递任何信息");
                res.put("state",0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         state = gson.toJson(res);
        logger.info("state---------------------" +state);
        return state;
    }
}
