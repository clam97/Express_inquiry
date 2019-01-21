package org.Dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.ocr.AuthService;
import org.ocr.PictureUtil;

import java.util.Map;


public class OCRSearch {
    private static Logger logger = Logger.getLogger(OCRSearch.class);
    public static String ocrSearch(String date){

        Map map = (Map) JSON.parse(date);
        String base64 = String.valueOf(map.get("base64"));
        String uuid = String.valueOf(map.get("uuid"));

        //logger.info("base64----------"+base64);
        //logger.info("uuid------------"+uuid);

        String message=null;
        String state;

        String httpUrl = " https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token=" + AuthService.getAuth();
        String httpArg = "image=" + base64;
        String jsonResult = PictureUtil.request(httpUrl, httpArg);
        System.out.println("返回的结果---------> " + jsonResult);

        System.out.println("-----------------------------1-------------------------------");

        JSONObject jsonObject = JSONObject.parseObject(jsonResult);
        System.out.println("-----------------------------2-------------------------------");
        String words_result = jsonObject.getString("words_result");
        System.out.println("-----------------------------3-------------------------------");
        JSONArray result_array = JSON.parseArray(words_result);
        System.out.println("-----------------------------4-------------------------------");
        for (int i = 0; i < result_array.size(); i++) {
            System.out.println("-----------------------------5-------------------------------");
            System.out.println(result_array.getJSONObject(i).getString("words"));
            message+= result_array.getJSONObject(i).getString("words")+" ";

        }
        System.out.println("=================111111111111"+message);
        //识别OCR翻译后的关键信息(待写)
        String expCode="";
        String expNo="";
        CommonSearch commonSearch = new CommonSearch();
        state = commonSearch.commonSearch(uuid,expCode,expNo);

        logger.info("state=----------"+state);
        return state;
    }

    public static void main(String[] args) {
        String data = "{\n" +
                "\t\"base64\": \"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH / 2 wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH / wAARCAjcD8ADASIAAhEBAxEB / 8 QAHwAAAQQCAwEBAAAAAAAAAAAABAIDBQYBBwAICQoL / 8 QAbRAAAgEBBAQJCQYEBAMFAAElAgMBBAAREhMFITFBBiIjM1FhcYHwBxQyQ1NjkaGxCHPB0eHxCSRCgxU0UpNEVKMKFmJksyVydMPTFzWChJTjRZKkGCZVorTzdYXCxDay1OQZZZWl0vRGVvLF / 8 QAHgEBAAMBAQEBAQEBAAAAAAAAAAECAwQFBgcJCAr / xABdEQABAgEICQMDAgQDBgMDARkAAQIRAwQSITFBUfAiYXGBkaGxwdEFMuEGE / EUQgcjJDMINFIVQ0RUYmR0hJQJFiVTgjVjcqS0RXOissLEF1WS0tRlk + J1g7Pk9P / aAAwDAQACEQMRAD8A + NfRspcBVAugXpLMERy4Ab/Vrvnp698XWZZSqKrmBKRaSDEsI8RjG+r6N238LrMpo1qgTQqlpVkXopSsJ/2+nb42yiokyGZWMGLMQ5beIOqL2M7NfjVb6NqPY2i1IJtTzDkeK1tFtFvCutdmHQd0a6tpaeaMGExQpDKW5mMxPMu5O7x07bcNYDEkycWYWJjRjjlynj9YsTkmQRV04MkFlhYwuPhuP2n47btVosqhxGesZSRYlvGOJjVsXt+Frta1qaN9+cqdKUqKUo21YeI22D8JOCC4omRLEN4+7/S7t32IVUo0bEKBQqx5zFrzM6EsqmNd5xDOjOZf87EoIyuwKi / FixCzida19PZ1RExbDKVZAazWUGXGLCWMC5TbmeNfda8Vo0auGao146yyq5fdHZmHEzGmKkmU7s6RqKdZrY7kzMs1eVmM6469V++zD3te\",".replace(" ","") +
                "\t\"uuid \": \"21423214 \"" +
                "}";
        OCRSearch.ocrSearch(data);
    }
}
