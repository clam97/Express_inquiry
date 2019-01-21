package org.SqlDeal;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class Updatepasswd {
    private static Logger logger = Logger.getLogger(Updatepasswd.class);
    private Statement stmt = null;
    private Connection conn = null;
    String state = null;
    Map res = new HashMap();
    Gson gson = new Gson();
    public String updatepasswd(String email,String passwd){
        try {
            conn = JDBCUtils_JDBC.getConnection();

            System.out.println("==============1=======================");
            String sql = "update user set password = "+passwd+" where email = '"+email+"'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("sql------------"+sql);
            logger.info("sql------"+sql);
            logger.info("密码修改成功");
            res.put("state",1);
        } catch (SQLException e) {
            res.put("state",0);
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            JDBCUtils_JDBC.release(conn,stmt);
        }
        state = gson.toJson(res);
        return  state;
    }

    public static void main(String[] args) {
        Updatepasswd updatepasswd = new Updatepasswd();
        String email = "1250743611@qq.com";
        String newpassword = "1111111";
        updatepasswd.updatepasswd(email,newpassword);
    }
}
