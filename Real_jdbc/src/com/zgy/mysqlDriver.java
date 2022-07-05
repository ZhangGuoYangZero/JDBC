package com.zgy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class mysqlDriver {
    static  Properties  properties = new Properties();
    String  url,user,password;
    Connection conn;
    static {
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("conf.properties"));
        }catch (IOException e){
            System.out.println("not file");
        }
    }
    public  mysqlDriver(){
        try {
            Class.forName(properties.getProperty("driverPath"));
            this.url = properties.getProperty("url");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
            this.conn = DriverManager.getConnection(url,user,password);
        }catch (ClassNotFoundException e){} catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection  getConn(){
        return  this.conn;
    }

}
