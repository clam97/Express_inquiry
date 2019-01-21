package org.Dao;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.SqlDeal.JDBCUtils_JDBC;
import org.Util.MakeUUid;
import org.Util.MyEmailUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class ResighterDeal {
    private static Logger logger = Logger.getLogger(ResighterDeal.class);

    public static int Code(){//生成随机数
        Random random = new Random();
        int r = random.nextInt(10000);
        return r;
    }
    public static String reqRandom(String date){//调用发送邮箱类返回Json字符串
        logger.info("-------------匹配接口 random 成功！已进入方法-------------------");
        Jedis jedis = new Jedis("127.0.0.1",6379);
        Map map = (Map) JSON.parse(date);
        String email = String.valueOf(map.get("email"));
        logger.info("接收的email为："+email);
        System.out.println("ssssssssssss"+email);
        int r = Code();
        String code ="您正在注册快递查的帐号，收到的验证码"+r;
        jedis.set(email,String.valueOf(r));
        MyEmailUtil.doSend(email,code);
        System.out.println("====================================================");

        Map<String,Integer> states = new HashMap<String, Integer>();
        states.put("state",1);
        Gson gson = new Gson();
        String state = gson.toJson(states);
        System.out.println(state);
        logger.info("返回的结果为："+state);

        return state;
    }


    public static String SuccessMessage(String date){
        logger.info("-------------匹配接口 register 成功！已进入方法-------------------");
        Jedis jedis = new Jedis("127.0.0.1",6379);
        Map map = (Map)JSON.parse(date);
        String random = String.valueOf(map.get("random"));
        String email = String.valueOf(map.get("email"));
        String password = String.valueOf(map.get("password"));

        logger.info("获得的随机数random为："+random);
        logger.info("获得的email："+email);
        logger.info("获得的password："+password);
        Map <String,Object>res = new HashMap();
        Gson gson = new Gson();
        if (random.equals(jedis.get(email))){//随机数匹配成功 落库并给前台回状态

            Connection conn=null;
            PreparedStatement ps=null;
            try {
                String uuid = MakeUUid.getUUID();
                conn=JDBCUtils_JDBC.getConnection();
                System.out.println("*******************************************************************************************************************");
                String sql="insert into user(uuid,email,password) values (?,?,?);";
                ps=conn.prepareStatement(sql);
                ps.setString(1,uuid);
                ps.setString(2,email);
                ps.setString(3,password);
                ps.executeUpdate();

                logger.info("sql语句为："+sql);

                res.put("state",2);
                res.put("userid",uuid);
                logger.info("注册成功");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                res.put("state",0);
                logger.info("注册失败！");

            }finally {
                //释放资源
                JDBCUtils_JDBC.release(conn,ps);
            }

        }
        String state = gson.toJson(res);
        logger.info("返回的结果  state为："+state);
        return state;
    }


//    public static void main(String[] args) {
//        String data="{\"email\":\"1250743611@qq.com\"}";
//        System.out.println("------------1----------");
//      ResighterDeal.reqRandom(data);
//
//
//
//       System.out.println("--------------2--------");
//
//
//    }
}
