package edu.gatech.cs2340.wheresmystuff.model;

import java.util.HashMap;

/**
 * @author Hartley McGuire
 * @version 1.0
 */

public class FakeFirebase {
    private static final FakeFirebase ourInstance = new FakeFirebase();
    public static FakeFirebase getInstance() {
        return ourInstance;
    }

    private User user;
    private HashMap<String, User> userList = new HashMap<>();

    private FakeFirebase() {
    }

    public boolean register(String email, String password) {
        if (userList.containsKey(email)) {
            return false;
        } else {
            User newUser = new User(email, password);
            userList.put(email, newUser);
            user = newUser;
            return true;
        }
    }

    public boolean registerAdmin(String email, String password) {
        if (userList.containsKey(email)) {
            return false;
        } else {
            User newUser = new Admin(email, password);
            userList.put(email, newUser);
            user = newUser;
            return true;
        }
    }

    public boolean login(String email, String password) {
        if (userList.containsKey(email)) {
            user = userList.get(email);
            return true;
        } else {
            return false;
        }
    }

    public void signOut() {
        user = null;
    }
}
