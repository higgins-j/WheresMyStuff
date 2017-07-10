package edu.gatech.cs2340.wheresmystuff.model;

import java.util.Date;

/**
 * @author John Abrams
 * @version 1.0
 */

/**
 * This class represents a credit card
 */
public class CreditCard {

    private String name;
    private int cardNum;
    private Date expirDate;
    private int secCode;

    /**
     * Constructor
     * @param name card owner's name
     * @param cardNum the credit card number
     * @param expirDate the card's expiration date
     * @param secCode the card's security code
     */
    public CreditCard(String name, int cardNum, Date expirDate, int secCode) {
        this.name = name;
        this.cardNum = cardNum;
        this.expirDate = expirDate;
        this.secCode = secCode;
    }
}
