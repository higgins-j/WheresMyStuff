package edu.gatech.cs2340.wheresmystuff.model;

import java.util.ArrayList;

/**
 * This class represents a user
 * @author Monira Khan & John Abrams
 * @version 1.2
 */

class User {
    private String user;
    private String pass;
    private final ArrayList<Item> lostItems = new ArrayList<>();
    private final ArrayList<Item> foundItems = new ArrayList<>();
    private final ArrayList<Item> neededItems = new ArrayList<>();

    /*
    Constructor
     */
    User(String user, String pass) {
        this.user = user;
        this.pass = pass;
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

    public ArrayList<Item> getLostItems() {
        return lostItems;
    }

    public ArrayList<Item> getFoundItems() {
        return foundItems;
    }

    public ArrayList<Item> getNeededItems() {
        return neededItems;
    }

    /**
     * Adds a lost item that the user lost and is looking for into the array
     * list of their lost items
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
     * Adds a needed item that the user needs into the array list of their needed
     * items
     * @param  neededItem [item to add into the list]
     * @return            [returns if it was successful]
     */
    public boolean addNeededItem(Item neededItem) {
        return neededItems.add(neededItem);
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