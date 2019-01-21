package org.Dao;

import com.alibaba.fastjson.JSON;
import org.SqlDeal.JDBCUtils_JDBC;
import org.SqlDeal.Selectjilu;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Map;

public class Searchjilu {//查看查询记录的处理
    private static Logger logger = Logger.getLogger(Searchjilu.class);
    public static String searchjilu(String date){
        String state = null;

        Map map = (Map) JSON.parse(date);
        String uuid = String.valueOf(map.get("uuid"));
        try {
            JDBCUtils_JDBC.getConnection();
            state = Selectjilu.selectjilu(uuid);
            System.out.println(state);
            logger.info("返回的state值为："+state);


        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


        return state;

    }



}
