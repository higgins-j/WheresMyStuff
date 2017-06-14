package edu.gatech.cs2340.wheresmystuff.model;

public class Login {
    private static final String username = "username";
    private static final String password = "password";

    public boolean check(String user, String pass) {
        return (user.equals(username) && pass.equals(password));
    }
}
