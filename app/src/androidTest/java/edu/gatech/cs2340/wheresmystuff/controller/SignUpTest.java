package edu.gatech.cs2340.wheresmystuff.controller;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.gatech.cs2340.wheresmystuff.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class SignUpTest {

    final private String goodUsername = "test@gatech.edu";
    final private String goodPassword = "password";
    final private String badPassword = "123456";


    private ViewInteraction email;
    private ViewInteraction password;
    private ViewInteraction passwordTwo;
    private ViewInteraction button;

    @Rule
    public ActivityTestRule<SignUpActivity> activityRule = new ActivityTestRule<>(
            SignUpActivity.class);


    @Before
    public void setUp() {
        email = onView(withId(R.id.email));
        password = onView(withId(R.id.password_one));
        passwordTwo = onView(withId(R.id.password_two));
        button = onView(withId(R.id.email_sign_in_button));


    }


    @Test
    public void testAttemptSignupBlankPassword() {
        email.perform(typeText(goodUsername));
        closeSoftKeyboard();
        button.perform(click());
        password.check(matches(hasErrorText("This field is required")));
    }

    @Test
    public void testAttemptSignupShortPassword() {
        email.perform(typeText(goodUsername));
        password.perform(typeText(badPassword));
        passwordTwo.perform(typeText(badPassword));
        closeSoftKeyboard();
        button.perform(click());
        password.check(matches(hasErrorText("This password is too short")));
    }

    @Test
    public void testAttemptMismatchingPasswords() {
        email.perform(typeText(goodUsername));
        password.perform(typeText(goodPassword));
        passwordTwo.perform(typeText(badPassword));
        closeSoftKeyboard();
        button.perform(click());
        password.check(matches(hasErrorText("Passwords do not match")));
        passwordTwo.check(matches(hasErrorText("Passwords do not match")));
    }

    @Test
    public void testAttemptSignupBlankEmail() {
        password.perform(typeText(goodPassword));
        passwordTwo.perform(typeText(goodPassword));
        closeSoftKeyboard();
        button.perform(click());

        email.check(matches(hasErrorText("This field is required")));
    }

    @Test
    public void testAttemptSignupBadEmail() {
        String badUsername = "xxx";
        email.perform(typeText(badUsername));
        password.perform(typeText(goodPassword));
        passwordTwo.perform(typeText(goodPassword));
        closeSoftKeyboard();
        button.perform(click());

        email.check(matches(hasErrorText("This email address is invalid")));
    }

    @Test
    public void testAttemptSignupGood() {
        email.perform(typeText(goodUsername));
        password.perform(typeText(goodPassword));
        passwordTwo.perform(typeText(goodPassword));
        closeSoftKeyboard();
        button.perform(click());


    }







}

