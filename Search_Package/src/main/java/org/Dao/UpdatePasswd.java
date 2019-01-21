package org.Dao;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.SqlDeal.Updatepasswd;
import org.Util.MyEmailUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class UpdatePasswd {
    private static Logger logger = Logger.getLogger(UpdatePasswd.class);

    public String updatewdsend(String data){
        Map map = (Map) JSON.parse(data);
        Map res = new HashMap();
        Gson gson = new Gson();
        String state = null;
        String email = String.valueOf(map.get("email"));

        int r = ResighterDeal.Code();
        String code = "正在进行密码更改，您收到的验证码为："+r;
        int ret =MyEmailUtil.doSend(email,code);
        if (ret==1){
            res.put("state",1);

            }else {
            res.put("state",0);
        }

        state = gson.toJson(res);

        logger.info("state               "+state);
        return state;
    }

    public String newpassword(String data){
        String state;
        Map map = (Map) JSON.parse(data);
        String email = String.valueOf(map.get("email"));
        String newpasswd = String.valueOf(map.get("newpasswd"));

        Updatepasswd updatepasswd = new Updatepasswd();
        state = updatepasswd.updatepasswd(email,newpasswd);

        return state;
    }

    public static void main(String[] args) {
        UpdatePasswd updatePasswd = new UpdatePasswd();
        String data = "{\"email\":\"1250743611@qq.com\"}";
        updatePasswd.updatewdsend(data);

    }
}
