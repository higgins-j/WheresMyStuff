package edu.gatech.cs2340.wheresmystuff.model;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.Date;
import java.util.Calendar;
/**
 * Represents an Item
 */

public class Item {

    private Category category;
    private Status status;
    private String userID;
    private String title;
    private String description;
    private int monetaryValue;
    private Date dateAdded;

    /**
     * Default constructor necessary for Firebase serialization
     */
    public Item() {}

    public Item(String title, String description, Category category, Status status, String userID,
                int monetaryValue) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.userID = userID;
        this.monetaryValue = monetaryValue;
        //this.dateAdded = idk how to set current date without using LocalDate class which is only java 8
    }

    /**
     * Added method for getting the Category as an enum since Firebase used the default get()
     * @return category as a Category
     */    @Exclude
    public Category getCategoryVal() {
        return category;
    }

    public String getCategory() {
        if (category == null) {
            return null;
        } else {
            return category.toString();
        }
    }

    public void setCategory(String category) {
        if (category == null) {
            this.category = null;
        } else {
            this.category = Category.valueOf(category);
        }
    }

    /**
     * Added method for getting the Status as an enum since Firebase used the default get()
     * @return status as a Status
     */
    @Exclude
    public Status getStatusVal() {
        return status;
    }

    public String getStatus() {
        if (status == null) {
            return null;
        } else {
            return status.toString();
        }
    }

    public void setStatus(String status) {
        if (status == null) {
            this.status = null;
        } else {
            this.status = Status.valueOf(status);
        }
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(int monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public enum Category {
        CLOTHING, JEWELRY, TECH, TOY, HOUSEHOLD_APPLIANCE, PHOTO
    }

    public enum Status {
        FOUND, LOST, NEEDED
    }
}