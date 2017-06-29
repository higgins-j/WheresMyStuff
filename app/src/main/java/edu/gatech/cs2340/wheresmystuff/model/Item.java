package edu.gatech.cs2340.wheresmystuff.model;

import java.util.List;
import java.util.Arrays;


/**
 * @author Monira Khan
 * @version 1.0
 */

/**
 * Represents an Item
 */

public class Item {
    public static List<String> legalCategories = Arrays.asList("CLOTHING",
        "JEWELRY", "TECH", "TOY", "HOUSEHOLD_APPLIANCE", "PHOTO");
    public static List<String> legalStatuses = Arrays.asList("FOUND", "LOST",
        "NEEDED");

    private User owner;
    private String category;
    private String status;
    private String description;
    private int monetaryValue;

}