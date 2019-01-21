package org.ocr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;

public class Test {
    public static void main(String[] args) {
      File f = new File("/home/wwj/File/12.png");
        String imageBase = Base64Test.encodeImgageToBase64(f);
        imageBase = imageBase.replaceAll("\r\n", "");
        imageBase = imageBase.replaceAll("\\+", "%2B");
        System.out.println(imageBase.getBytes().length);
        String httpUrl = " https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token=" + AuthService.getAuth();
        String httpArg = "image=" + imageBase;
        String jsonResult = PictureUtil.request(httpUrl, httpArg);
        System.out.println("返回的结果---------> " + jsonResult);
        JSONObject jsonObject = JSONObject.parseObject(jsonResult);
        String words_result = jsonObject.getString("words_result");
        JSONArray result_array = JSON.parseArray(words_result);
        for (int i = 0; i < result_array.size(); i++) {
            System.out.println(result_array.getJSONObject(i).getString("words"));
        }
    }
}
