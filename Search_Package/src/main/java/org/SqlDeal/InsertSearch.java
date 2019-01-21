package org.SqlDeal;


import org.Util.MakeUUid;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class InsertSearch {
    private static Logger logger = Logger.getLogger(InsertSearch.class);

    public static void insertSearch(String uuid,String expCode,String expNo){
        Map res = new HashMap();
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            //String uuid = MakeUUid.getUUID();
            conn=JDBCUtils_JDBC.getConnection();
            System.out.println("*******************************************************************************************************************");
            String sql="insert into jilu(uuid,expCode,expNo) values (?,?,?)";

            logger.info("将 userid,expCode,expNo 插入数据库sql语句为："+sql);
            ps=conn.prepareStatement(sql);
            ps.setString(1,uuid);
            ps.setString(2,expCode);//公司编码
            ps.setString(3,expNo);//快递单号
            ps.executeUpdate();

            logger.info("将 userid,expCode,expNo 插入数据库成功");
            res.put("state",1);
            System.out.println("插入成功");
        } catch (SQLException e) {
            e.printStackTrace();
            res.put("state",0);
            System.out.println("插入失败");
        }finally {
            //释放资源
            JDBCUtils_JDBC.releases(conn,ps);
        }
    }
}
