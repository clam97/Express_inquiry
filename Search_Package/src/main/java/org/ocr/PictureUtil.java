package org.ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PictureUtil {
    public static String request(String httpUrl, String httpArg ) {
        BufferedReader reader = null;
        AuthService authService = new AuthService();
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            // 用java JDK自带的URL去请求
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置该请求的消息头
            // 设置HTTP方法：POST
            connection.setRequestMethod("POST");
            // 设置其Header的Content-Type参数为application/x-www-form-urlencoded
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "ejQ7MHBBuT1YWwjaLZCKTxj5");
            connection.setRequestProperty("probability","true");
//            connection.setRequestProperty("image",imageBase);
            // 将第二步获取到的token填入到HTTP header
//            connection.setRequestProperty("access_token", authService.getAuth());
            connection.setDoOutput(true);
            connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
            connection.connect();
//            if(connection.getResponseMessage().equals("OK")){
//                f.delete();
//            }
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
