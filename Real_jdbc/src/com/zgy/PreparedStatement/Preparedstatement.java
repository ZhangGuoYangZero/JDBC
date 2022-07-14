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
         * ����ע��
         @param :
         @return:void
         @Description: ͨ�õĹ������� ɾ�� �޸ĵĴ���
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
            //Ԥ����
            preparedStatement = conn.prepareStatement(sql);
            //���ò���
            if (args != null)
                for (int i = 1; i <= args.length; i++) {
                    preparedStatement.setObject(i, args[i - 1]);
                }
            //ִ��sql
            int bool = preparedStatement.executeUpdate();
            //�����Ƿ�ִ����ȷ
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
         * ����ע��
         @param :
         @return:void
         @Description: ������дͨ�õĲ�ѯ��䣬����ʹ�����ݿ�����˼�룬����ǰ֪����ͱ��ֶΣ�������ǰ����һ������
         ����ֶ���ͬJAVA�е��ֶ�����һ��������ʹ�ö������ﶼ�Ƿ�������У���Ϊ���Ƿ���ʹ��
         */
        Connection conn = Mysqldriver.getConnection();
        /*ps = ct.prepareStatement("select ? from staffs"); �������Ĳ������ֶ�����
         �Ͳ�����prepareStatementռλ����д�����������������������''��������д��ִ�е�sql����
        select ��EntNum�� from staffs���������Ȼȫ�ǹ̶����ֶ��� ��������������ַ���ƴ�ӵķ�ʽ������sql����Ҫ��ռλ����д��

       ռλ��ֻ���滻ֵ���ͣ������滻�������ֶ������������ؼ��ʡ�
        PreparedStatement��Ϊռλ��?�������Զ����ϵ����ţ�������ʹ��SQL��䲻��ִ�У�
        ����ʹ�ý���������Ϊռλ�������ݿ�ִ��sql���ʱ


        ���� ?ֻ���������ֵ��λ�ã�Ҳ����SQL�еĿɱ�λ�ã���������������SETXX��˵���ˡ����Բ�������ֶΣ����ؼ���
       * */

        String sql = "select name,age,bouns from zgyjdbc where name = ?";
        ArrayList<Object> o = new Preparedstatement().commQuery(sql, conn, new Zgyjdbc(), "����");
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
            //��ȡ��ʹ������Ԥ����
            preparedStatement = conn.prepareStatement(sql);
            //���ò���������������˱����⣬�п������ֶ����֣��п�����where����groupby��Щ,��������Щû���漰��DAO����
            if (args != null)
                for (int i = 1; i <= args.length; i++) {
                    preparedStatement.setObject(i, args[i - 1]);
                }
            result = preparedStatement.executeQuery();
            if (result != null) {
                //��ȡresult����Ľ�������next�����������������hasnext��һ����hasnext�����£�ֻ�ж��¡�
                // �������next�ж��о����£��������������next�ֲ�һ������������������ȡ��һ����

                //������Ҫһ������ �����������������DAO˼��ʵ������,���������ᱻ��Ϊ����ʹ��
                ArrayList<Object> arrayList = new ArrayList<>();

                //����ȻDAO��������ֶ���ʲô���������е�ʱ�򣬳����ǲ�֪��DAO������ʲô�ġ��ұ����ó���֪��DAO������ʲô��ֻ��ͨ��RESULT�Ľ��������Ķ�������֪������
                ResultSetMetaData metaData = result.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (result.next()) {
                    Object dom = obj.getClass().getDeclaredConstructor().newInstance();
                    //һ����¼������columnCount���ֶΣ���ÿ���ֶζ���Ҫ�õ������ֶ����������䣬��������
                    for (int i = 0; i < columnCount; i++) {
                        Object refelectName = metaData.getColumnLabel(i + 1);
                        Object value = result.getObject(i + 1);
                        //ͨ�������ȡ�ֶ�
                        Field declaredField = obj.getClass().getDeclaredField((String) refelectName);
                        declaredField.setAccessible(true);
                        //set������ֵ
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
         * ����ע��
         @param :
         @return:void
         @Description: PreparedStatementbatch����, delete update �����д�����������������
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
            //����������������������
            for (int i = 1; i <= count; i++) {
                preparedStatement.setObject(2,args[1]);
                preparedStatement.addBatch();
                if(i % 50 == 0){
                    //ִ�л����˵�batch
                    preparedStatement.executeBatch();
                    System.out.println(i+ "Execute");
                    //ɾ�������batch
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



