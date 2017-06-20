package edu.gatech.cs2340.wheresmystuff.model;

/**
 * @author Justin Higgins
 * @version 1.0
 *
 * This class will be used to check login information.
 */
public class Login {
    private static final String username = "username";
    private static final String password = "password";

    /**
     *
     * @param user username entered by the user in the login screen
     * @param pass password entered by the user in the login screen
     * @return true if credentials are correct, false if they are incorrect
     */
    public static boolean check(String user, String pass) {
        return (user.equals(username) && pass.equals(password));
    }
}
