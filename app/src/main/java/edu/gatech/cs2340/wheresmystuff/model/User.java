package edu.gatech.cs2340.wheresmystuff.model;

import java.util.ArrayList;



/**
 * @author Monira Khan & John Abrams
 * @version 1.2
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
    private ArrayList<Item> lostItems = new ArrayList<>();
    private ArrayList<Item> foundItems = new ArrayList<>();
    private ArrayList<Item> neededItems = new ArrayList<>();
    private double balance;
    private ArrayList<CreditCard> cards;


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
        this.balance = 0;

    }

    public User(String user, String pass) {
        this(user, pass, null, null, null, AccountState.ACTIVE);

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

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public AccountState getAccountState() {
        return accountState;
    }

    public AccountState setAccountState(AccountState newAccountState) {
        accountState = newAccountState;
        return accountState;
    }

    public ArrayList<Item> getLostItems() {
        return lostItems;
    }

    public ArrayList<Item> getFoundItems() {
        return foundItems;
    }

    public ArrayList<Item> getNeededItems() {
        return neededItems;
    }

    public double getBalance() { return balance; }

    public ArrayList<CreditCard> getCards() { return cards; }

    /**
     * Adds a lost item that the user lost and is looking for into the array
     * listof their lost items
     * @param lostItem [item to add into the list]
     * @return boolean is it was successful
     */
    public boolean addLostItem(Item lostItem) {
        return lostItems.add(lostItem);
    }

    /**
     * Adds a found item that user found and is willing to give away/sell to the
     * array of their found items
     * @param foundItem [item to add into the list]
     * @return returns if it was successful
     */
    public boolean addFoundItem(Item foundItem) {
        return foundItems.add(foundItem);
    }

    /**
     * Adds a needed item that the user needs into the aray list of their needed
     * items
     * @param  neededItem [item to add into the list]
     * @return            [returns if it was successful]
     */
    public boolean addNeededItem(Item neededItem) {
        return neededItems.add(neededItem);
    }

    /**
     * Adds a credit card to the user's account
     * @param card the credit card being added
     * @return true if it was successful and false otherwise
     */
    public boolean addCard(CreditCard card) {
        return cards.add(card);
    }

    /**
     * Adds a monetary amount to user's balance
     * @param amount the monetary amount being added
     */
    public void addToBal(double amount) {
        balance = balance + amount;
    }

    /**
     * Subtracts a monetary amount from user's balance
     * @param amount the monetary amount being subtracted
     */
    public void subtractFromBal(double amount) {
        balance = balance - amount;
    }

    /**
     * Reports a user for bad behavior
     * @param user the user being reported
     * @return the user being reported
     */
    public User reportUser(User user) {
        return user;
    }
}