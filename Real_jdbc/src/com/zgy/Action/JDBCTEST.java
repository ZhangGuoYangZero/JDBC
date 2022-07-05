package com.zgy.Action;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTEST {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {

    }


    @Test
    public void getConnection1() throws Exception {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        System.out.print(driver);
    }

    public void getConnection2() throws Exception {
        try {
            Class DriverClass = Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = (Driver) DriverClass.getDeclaredConstructor().newInstance();
            String url = "jdbc:mysql://localhost:3306/jdbc";
            Properties info = new Properties();
            info.setProperty("user", "zgy");
            info.setProperty("password", "123456");
            Connection connect = driver.connect(url, info);
            System.out.println(connect);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("尴尬");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void getConnection3() throws RuntimeException, SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //这是JAVA JDK里面自带的链接管理器，不是MYSQL包里面的。这个类是基础类
        DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance());
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "zgy", "123456");
        System.out.println(conn);
    }

    public void getConnection4() throws ClassNotFoundException, SQLException {
        //在原来的基础上MYSQL自行加载不需要进行register
        Class.forName("com.mysql.cj.jdbc.Driver");
        DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "zgy", "123456");

    }

    /*

    这种方法的好处就是可以不动代码就可以切换登陆账户和数据库
     * */
    @Test
    public void getConnection5() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        //JDBCTEST.CLASS 是获取这个类的对象，不是类对象，就是JVM识别的对象（THIS），然后的得到这个对象的资源文件，作为一个输入流
        InputStream inputStreamProperty = JDBCTEST.class.getClassLoader().getResourceAsStream("conf.properties");
        properties.load(inputStreamProperty);
        //FORNAME加载又初始化，比loader好
        Class.forName(properties.getProperty("driverPath"));
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        Connection conn = DriverManager.getConnection(url, user, password);
    }

}
