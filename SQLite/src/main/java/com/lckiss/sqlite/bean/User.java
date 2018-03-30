package com.lckiss.sqlite.bean;

/**
 * Created by deng_ on 2017/4/5.
 */

public class User {
    private  String name=null;
    private String number=null;

    public User() {
    }

    public User(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
