package org.Dao;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.SqlDeal.JDBCUtils_JDBC;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class LoginDeal {//密码登录验证
    private static Logger logger = Logger.getLogger(LoginDeal.class);
   public static String login(String data){

       logger.info("-------------匹配接口 login 成功！已进入方法-------------------");
       Map res =new HashMap();
       Gson gson = new Gson();
       Connection conn=null;
       Statement stamt=null;
       ResultSet rs=null;

       Map map = (Map) JSON.parse(data);
      String email = String.valueOf(map.get("email"));
      String passwd = String.valueOf(map.get("password"));
      String sql="select *from user where email= '"+email+"';";

      logger.info("得到的email为"+email);
      logger.info("得到的password为"+passwd);
      logger.info("LoginDeal 打印的sql语句为"+sql);

       try {
           conn=JDBCUtils_JDBC.getConnection();
           stamt=conn.createStatement();
           rs=stamt.executeQuery(sql);
           if (rs.next()){

               String password = rs.getString("password");
               String uuid = rs.getString("uuid");
               logger.info("查库的password---------"+password);
               if (passwd.equals(password)){
                   res.put("state",1);
                   res.put("uuid",uuid);
                   System.out.println("密码正确 可以登录");
               }else {
                   res.put("state",0);
                   System.out.println("密码错误");
               }
           }
       } catch (SQLException e) {
           logger.error(e.getMessage());
           e.printStackTrace();
       }

       String state = gson.toJson(res);
       logger.info("返回的结果state 为："+state);

       return state;
   }
}
