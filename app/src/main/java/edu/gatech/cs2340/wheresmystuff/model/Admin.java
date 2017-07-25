package edu.gatech.cs2340.wheresmystuff.model;

/**
 * This class represents an Admin
 * @author Monira Khan
 * @version 1.0
 */
class Admin extends User {
    public Admin(String user, String pass, String name, String phoneNum,
        String address, AccountState accountState) {
        super(user, pass, name, phoneNum, address, accountState);
    }

    public Admin(String user, String pass) {
        super(user, pass);
    }

    /**
     * Bans user
     * @param  bannedUser the user being banned
     */
    public void banUser(User bannedUser) {
        bannedUser.setAccountState(AccountState.BANNED);
    }

    /**
     * Unlocks a user's account
     * @param unLocked the user account being unlocked
     */
    public void unLockAccount(User unLocked) {
        unLocked.setAccountState(AccountState.ACTIVE);
    }

 }