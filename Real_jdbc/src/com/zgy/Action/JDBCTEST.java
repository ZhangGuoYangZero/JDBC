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
            throw new Exception("����");
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
        //����JAVA JDK�����Դ������ӹ�����������MYSQL������ġ�������ǻ�����
        DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance());
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "zgy", "123456");
        System.out.println(conn);
    }

    public void getConnection4() throws ClassNotFoundException, SQLException {
        //��ԭ���Ļ�����MYSQL���м��ز���Ҫ����register
        Class.forName("com.mysql.cj.jdbc.Driver");
        DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "zgy", "123456");

    }

    /*

    ���ַ����ĺô����ǿ��Բ�������Ϳ����л���½�˻������ݿ�
     * */
    @Test
    public void getConnection5() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        //JDBCTEST.CLASS �ǻ�ȡ�����Ķ��󣬲�������󣬾���JVMʶ��Ķ���THIS����Ȼ��ĵõ�����������Դ�ļ�����Ϊһ��������
        InputStream inputStreamProperty = JDBCTEST.class.getClassLoader().getResourceAsStream("conf.properties");
        properties.load(inputStreamProperty);
        //FORNAME�����ֳ�ʼ������loader��
        Class.forName(properties.getProperty("driverPath"));
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        Connection conn = DriverManager.getConnection(url, user, password);
    }

}
