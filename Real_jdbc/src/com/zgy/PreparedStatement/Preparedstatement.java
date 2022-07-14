package com.zgy.PreparedStatement;

import com.zgy.Mysqldriver;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Preparedstatement {

    @Test
    public void test1() throws ParseException {
        /**
         * 方法注释
         @param :
         @return:void
         @Description: 通用的关于增加 删除 修改的代码
         */
        Connection conn = Mysqldriver.getConnection();
        //String sql = "INSERT INTO zgyjdbc(name,age,bouns,datee) values(?,?,?,?)";

        // String sql  = "UPDATE ZGYJDBC SET NAME = ? " +
        // "where name like ?";

        String sql = "delete from zgyjdbc";

        if (new Preparedstatement().crd(sql, conn, null) > 0)
            System.out.println("True");
        else
            System.out.println("False");
    }

    public int crd(String sql, Connection conn, String... args) {
        PreparedStatement preparedStatement = null;
        try {
            //预编译
            preparedStatement = conn.prepareStatement(sql);
            //设置参数
            if (args != null)
                for (int i = 1; i <= args.length; i++) {
                    preparedStatement.setObject(i, args[i - 1]);
                }
            //执行sql
            int bool = preparedStatement.executeUpdate();
            //返回是否执行正确
            return bool;

        } catch (SQLException SQL) {

        } finally {
            Mysqldriver.closeAll(conn, preparedStatement);
        }
        return 0;
    }

    @Test
    public void test2() {
        /**
         * 方法注释
         @param :
         @return:void
         @Description: 用于书写通用的查询语句，并且使用数据库对象的思想，既提前知道表和表字段，并且提前创建一个表类
         如果字段名同JAVA中的字段名不一样，可以使用对象，这里都是反射的运行，因为都是泛型使用
         */
        Connection conn = Mysqldriver.getConnection();
        /*ps = ct.prepareStatement("select ? from staffs"); 你这插入的参数是字段名，
         就不能用prepareStatement占位符的写法，用这个会给插入参数加上''，你这种写法执行的sql会变成
        select ’EntNum‘ from staffs，查出来当然全是固定的字段名 解决方法就是用字符串拼接的方式来生成sql，不要用占位符的写法

       占位符只能替换值类型，不能替换表名、字段名或者其他关键词。
        PreparedStatement会为占位符?的两边自动加上单引号，这样会使得SQL语句不可执行，
        比如使用将表名设置为占位符，数据库执行sql语句时


        所以 ?只能用来替代值的位置，也就是SQL中的可变位置，至于类型是由于SETXX就说明了。所以不能替代字段，表，关键字
       * */

        String sql = "select name,age,bouns from zgyjdbc where name = ?";
        ArrayList<Object> o = new Preparedstatement().commQuery(sql, conn, new Zgyjdbc(), "张三");
        if (o.size() != 0)
            for (Object z : o) {
                System.out.println(z);
            }
        else
            System.out.println("ffffff");
    }

    public ArrayList<Object> commQuery(String sql, Connection conn, Object obj, String... args) {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            //获取信使，并且预编译
            preparedStatement = conn.prepareStatement(sql);
            //设置参数，这里参数除了表面外，有可能是字段名字，有可能是where条件groupby这些,但由于这些没有涉及到DAO的类
            if (args != null)
                for (int i = 1; i <= args.length; i++) {
                    preparedStatement.setObject(i, args[i - 1]);
                }
            result = preparedStatement.executeQuery();
            if (result != null) {
                //获取result里面的结果，这个next函数跟迭代器里面的hasnext不一样，hasnext不往下，只判断下。
                // 而这里的next判断有就往下，跟迭代器里面的next又不一样，迭代器的往下又取走一个数

                //这里需要一个容器 ，这个容器是这个表的DAO思想实例的类,待会这个类会被作为容器使用
                ArrayList<Object> arrayList = new ArrayList<>();

                //我虽然DAO类里面的字段是什么，但在运行的时候，程序是不知道DAO里面是什么的。我必须让程序知道DAO里面是什么，只能通过RESULT的结果集里面的东西来告知程序了
                ResultSetMetaData metaData = result.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (result.next()) {
                    Object dom = obj.getClass().getDeclaredConstructor().newInstance();
                    //一条记录里面有columnCount个字段，而每个字段都需要拿到它的字段名用来反射，还有数据
                    for (int i = 0; i < columnCount; i++) {
                        Object refelectName = metaData.getColumnLabel(i + 1);
                        Object value = result.getObject(i + 1);
                        //通过反射获取字段
                        Field declaredField = obj.getClass().getDeclaredField((String) refelectName);
                        declaredField.setAccessible(true);
                        //set宿主，值
                        declaredField.set(dom, value);
                    }
                    arrayList.add(dom);
                }
                return arrayList;
            }
        } catch (SQLException SQL) {
            System.out.println("SQLEXCEPTION");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            Mysqldriver.closeAll(conn, preparedStatement, result);
        }
        return null;
    }


    @Test
    public void test3() {
        /**
         * 方法注释
         @param :
         @return:void
         @Description: PreparedStatementbatch处理, delete update 本身有带有批量操作的属性
         */
         Connection conn = Mysqldriver.getConnection();
         String sql  = "INSERT INTO zgyjdbc(name,age,bouns,datee) value(?,?,?,?)";
         Long start = System.currentTimeMillis();
         new Preparedstatement().InsertBatch(sql,conn,5000, "zgy", "24", "15000", "2022-8-31");
         Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public void InsertBatch(String sql, Connection conn, Integer count, String... args) {

        PreparedStatement preparedStatement = null;
        try {

            preparedStatement  = conn.prepareStatement(sql);
            if (args != null)
                for (int i = 1; i <= args.length; i++) {
                    if(i == 2)
                        continue;
                    preparedStatement.setObject(i,args[i-1]);
                }
            //缓存多个操作编程批处理操作
            for (int i = 1; i <= count; i++) {
                preparedStatement.setObject(2,args[1]);
                preparedStatement.addBatch();
                if(i % 50 == 0){
                    //执行缓存了的batch
                    preparedStatement.executeBatch();
                    System.out.println(i+ "Execute");
                    //删除缓存的batch
                    preparedStatement.clearBatch();
                }
            }
        }catch (SQLException SQL){
            SQL.getMessage();
        }finally {
            Mysqldriver.closeAll(conn,preparedStatement);
        }
    }


}



