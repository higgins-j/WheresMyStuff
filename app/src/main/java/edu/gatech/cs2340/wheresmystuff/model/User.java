package edu.gatech.cs2340.wheresmystuff.model;

/**
 * @author Monira Khan
 * @version 1.0
 */

/*
This class represents a user
 */

public class User {
    private String user;
    private String pass;
    private String name;
    private String phoneNum;
    private String address;
    private AccountState accountState;

    /*
    Constructor
     */
    public User(String user, String pass, String name, String phoneNum,
        String address, AccountState accountState) {
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.accountState = accountState;

    }

    public User(String user, String pass) {
        this(user, pass, null, null, null, true);

    }

    public String getUser() {
        return user;
    }

    public void setUser(String newUser) {
        this.user = newUser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String newPass) {
        this.pass = newPass;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String newPhoneNum) {
        phoneNum = newPhoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newAddress) {
        address = newAddress;

    }

    public AccountState getAccountState() {
        return accountState;
    }

    public AccountState setAccountState(AccountState newAccountState) {
        accountState = newAccountState;
        return accountState;
    }





}