package edu.gatech.cs2340.wheresmystuff.model;

/**
 * @author Monira Khan
 * @version 1.0
 */

 /**
  * This class represents an Admin
  */

 public class Admin extends User {
    public Admin(String user, String pass, String name, String phoneNum,
        String address, boolean accountState) {
        super(user, pass, name, phoneNum, address, accountState);
    }

    public Admin(String user, String pass) {
        super(user, pass);
    }

    /**
     * Removes inappropriate posts
     * @return [description]
     */
    public boolean removePost() {
        return false;
    }

    /**
     * Bans user
     * @param  bannedUser [description]
     * @return            [description]
     */
    public boolean banUser(User bannedUser) {
        return bannedUser.setAccountState(false);
    }

    /**
     * Makes user into an Admin
     * @param toBeAdmin [description]
     */
    public void userToAdmin(User toBeAdmin) {

    }

    /**
     * Unlocks a user's account
     * @param unLocked [description]
     */
    public void unLockAccount(User unLocked) {
        unLocked.setAccountState(true);
    }

 }