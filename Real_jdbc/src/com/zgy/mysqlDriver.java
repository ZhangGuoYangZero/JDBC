package com.zgy;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.sql.*;
import java.util.Properties;

/**
 * @Description: nul
 * @Date:2022/7/8 - 5:51
 */
public class Mysqldriver {
    public static Connection getConnection() {
        try {
            //造一个properties对象
            Properties properties = new Properties();
            //获取properties文件流,通过inputStream通过系统加载器
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("conf.properties");
            //获取properties文件的内容
            properties.load(inputStream);
            //获取4个元素，从properties对象中
            String driverpath = properties.getProperty("driverPath");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            //开始链接,先注册驱动
            Class.forName(driverpath);
            //通过驱动管理器获取链接
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (IOException ioe) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void closeAll(Connection conn, Statement statemt) {
        /**
         * 方法注释
         @param conn: 链接对象
         @param statemt: 信使对象
         @return:boolean：确定是否正确
         @Description:
         */
        try {
            if (statemt != null)
                statemt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException sql) {
            System.out.println("close异常");
        }

    }

    public static void closeAll(Connection conn, Statement statemt, ResultSet resultSet) {
        /**
         * 方法注释
         @param conn:
         @param statemt:
         @param resultSet:
         @return:void
         @Description:
         */
        try {
            if (resultSet != null)
                resultSet.close();
            if (statemt != null)
                statemt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException sql) {
            System.out.println("close异常");
        }

    }
}
