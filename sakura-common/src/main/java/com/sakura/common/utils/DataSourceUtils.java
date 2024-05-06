package com.sakura.common.utils;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库链接校验
 * @author wutun
 */
public class DataSourceUtils {

    public static boolean verifyDataSourceConn(String driverName,String url,String userName ,String passWord){
        boolean flag = true;
        Connection dbConn=null;
        try {
            Class.forName(driverName);
            System.out.println("加载驱动成功");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("加载驱动失败");
            flag = false;
        }
        try {
             dbConn=DriverManager.getConnection(url,userName,passWord);

            System.out.println("连接数据库成功");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("数据库连接失败");
            flag = false;
        }

        /**
         * 释放资源
         */
        if (dbConn !=null){
            try {
                dbConn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return flag;
    }
}
