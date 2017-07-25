package edu.gatech.cs2340.wheresmystuff;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.wheresmystuff.controller.SignUpActivity;
import edu.gatech.cs2340.wheresmystuff.model.User;
import static org.junit.Assert.*;

public class SignUpTest {
    private SignUpActivity signUpActivity;


    @Before
    public void setUp() {
        signUpActivity = SignUpActivity.getInstance();


    }

    @Test
    public void testSignUp() {
         //Trying to add a null email and password. Should show Error
         signUpActivity.email = null;
         signUpActivity.password = null;
         signUpActivity.passwordTwo = null;
        Assert.assertFalse("All Fields are required",
            signUpActivity.attemptSignUp());

        //When the two passwords don't match, an error will appear
        signUpActivity.email = "test@gmail.com";
        signUpActivity.password = "password";
        signUpActivity.passwordTwo = "xxx";
        Assert.assertFalse("Passwords don't match",
            signUpActivity.attemptSignUp());

        //When the email is invalid an error should appear
        signUpActivity.email = "xxxx";
        signUpActivity.password = "password";
        signUpActivity.passwordTwo = "password";
        Assert.assertFalse("Email is invalid", signUpActivity.attemptSignUp());

        //When trying to sign up with an emai that is already taken, an error will appear
        signUpActivity.email = "example@gmail.com";
        signUpActivity.password = "password";
        signUpActivity.passwordTwo = "password";
        Assert.assertFalse("Email is already in use",
                signUpActivity.attemptSignUp());

        //When email is valid and passwords match a user should be created succesfully
        signUpActivity.email = "test@gmail.com";
        signUpActivity.password = "password";
        signUpActivity.passwordTwo = "password";
        Assert.assertTrue("User succesfully added into system",
            signUpActivity.attemptSignUp)();



    }



}

