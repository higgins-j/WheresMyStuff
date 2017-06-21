package edu.gatech.cs2340.wheresmystuff.model;
/**
 * @author Monira Khan
 * @version 1.0
 */

/**
 * Represents an Item
 */

public class Item {
    public static List<String> legalCategories = Arrays.asList("CLOTHING",
        "JEWELARY", "TECH", "TOY", "HOUSEHOLD_APPLIANCE", "PHOTO");
    public static List<String> legalTypes = Arrays.asList("FOUND", "LOST",
        "NEEDED");

    private User owner;
    private String category;
    private String type;
    private String description;

}