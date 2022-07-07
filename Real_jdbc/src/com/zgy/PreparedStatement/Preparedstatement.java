package com.zgy.PreparedStatement;
import com.zgy.Mysqldriver;

import org.junit.jupiter.api.Test;

import java.sql.Connection;


public class Preparedstatement {

    @Test
    public void test1() {
        /**
         * 方法注释
        @param :
        @return:void
        @Description: 通用的关于增加 删除 修改的代码
        */
        Connection conn = Mysqldriver.getConnection();

    }

}



