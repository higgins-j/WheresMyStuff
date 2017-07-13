package edu.gatech.cs2340.wheresmystuff.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private LatLng latLng;

    /**
     * Default constructor necessary for Firebase serialization
     */
    public Item() {}

    public Item(String title, String description, Category category, Status status, String userID,
                int monetaryValue, LatLng latLng) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.userID = userID;
        this.monetaryValue = monetaryValue;
        this.dateAdded = new Date();
        this.latLng = latLng;
    }

    /**
     * Method for getting the LatLng as a LatLng because Firebase reads numbers
     * @return latLng as a LatLng
     */
    @Exclude
    public LatLng getLatLngVal() {
        return latLng;
    }

    public List<Double> getLatLng() {
        if (latLng == null) {
            return null;
        } else {
            ArrayList<Double> returnList = new ArrayList<>();
            returnList.add(latLng.latitude);
            returnList.add(latLng.longitude);
            return returnList;
        }
    }

    public void setLatLng(List<Double> list) {
        if (list == null) {
            latLng = null;
        } else {
            latLng = new LatLng(list.get(0), list.get(1));
        }
    }


    /**
     * Added method for getting the Category as an enum since Firebase used the default get()
     * @return category as a Category
     */
    @Exclude
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

    /**
     * Added method for getting the Date an item was created since Firebase needs a String
     * @return dateAdded as a Date
     */
    @Exclude
    public Date getDateAddedVal() {
        return dateAdded;
    }

    public Long getDateAdded() {
        if (dateAdded == null) {
            return null;
        } else {
            return dateAdded.getTime();
        }
    }

    public void setDateAdded(Long dateAdded) {
        if (dateAdded == null) {
            this.dateAdded = null;
        } else {
            this.dateAdded = new Date(dateAdded);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum Category {
        CLOTHING, JEWELRY, TECH, TOY, HOUSEHOLD_APPLIANCE, PHOTO
    }

    public enum Status {
        FOUND, LOST, NEEDED
    }
}