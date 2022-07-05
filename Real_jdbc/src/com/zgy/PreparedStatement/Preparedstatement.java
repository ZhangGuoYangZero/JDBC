package com.zgy.PreparedStatement;

import com.zgy.mysqlDriver;
import org.junit.jupiter.api.Test;

import java.sql.Connection;



public class Preparedstatement {

    @Test
    public void test1() {
    Connection conn = new mysqlDriver().getConn();
    }

}



