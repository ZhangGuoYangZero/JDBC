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
            //��һ��properties����
            Properties properties = new Properties();
            //��ȡproperties�ļ���,ͨ��inputStreamͨ��ϵͳ������
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("conf.properties");
            //��ȡproperties�ļ�������
            properties.load(inputStream);
            //��ȡ4��Ԫ�أ���properties������
            String driverpath = properties.getProperty("driverPath");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            //��ʼ����,��ע������
            Class.forName(driverpath);
            //ͨ��������������ȡ����
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
         * ����ע��
         @param conn: ���Ӷ���
         @param statemt: ��ʹ����
         @return:boolean��ȷ���Ƿ���ȷ
         @Description:
         */
        try {
            if (statemt != null)
                statemt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException sql) {
            System.out.println("close�쳣");
        }

    }

    public static void closeAll(Connection conn, Statement statemt, ResultSet resultSet) {
        /**
         * ����ע��
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
            System.out.println("close�쳣");
        }

    }
}
