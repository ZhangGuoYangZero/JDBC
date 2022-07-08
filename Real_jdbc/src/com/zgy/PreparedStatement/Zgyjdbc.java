package com.zgy.PreparedStatement;

import java.sql.Date;

/**
 * @Description:
 * @Date:2022/7/8 - 7:49
 */
public class Zgyjdbc {
    private String name;
    private  int    age;
    private  double bouns;
    private  Date   date;

    public Zgyjdbc(){}

    @Override
    public  String toString(){
        return  name + " " + age +" " + bouns + " " + date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBouns() {
        return bouns;
    }

    public void setBouns(double bouns) {
        this.bouns = bouns;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
