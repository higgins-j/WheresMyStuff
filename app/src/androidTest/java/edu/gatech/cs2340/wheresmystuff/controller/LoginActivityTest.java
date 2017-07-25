package edu.gatech.cs2340.wheresmystuff.controller;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.cs2340.wheresmystuff.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Hartley McGuire
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    private ViewInteraction email;
    private ViewInteraction password;
    private ViewInteraction button;

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void setup() {
        email = onView(withId(R.id.email));
        password = onView(withId(R.id.password));
        button = onView(withId(R.id.email_sign_in_button));
    }


    @Test
    public void testAttemptLoginBlankPassword() {
        email.perform(typeText("junit@gatech.edu"));
        closeSoftKeyboard();
        button.perform(click());

        password.check(matches(hasErrorText("This field is required")));
    }

    @Test
    public void testAttemptLoginShortPassword() {
        email.perform(typeText("junit@gatech.edu"));
        password.perform(typeText("123456"));
        closeSoftKeyboard();
        button.perform(click());

        password.check(matches(hasErrorText("This password is too short")));
    }

    @Test
    public void testAttemptLoginBlankEmail() {
        password.perform(typeText("password"));
        closeSoftKeyboard();
        button.perform(click());

        email.check(matches(hasErrorText("This field is required")));
    }

    @Test
    public void testAttemptLoginBadEmail() {
        email.perform(typeText("test"));
        password.perform(typeText("password"));
        closeSoftKeyboard();
        button.perform(click());

        email.check(matches(hasErrorText("This email address is invalid")));
    }

    @Test
    public void testAttemptLoginGood() {
        email.perform(typeText("junit@gatech.edu"));
        password.perform(typeText("password"));
        closeSoftKeyboard();
        button.perform(click());


    }
}