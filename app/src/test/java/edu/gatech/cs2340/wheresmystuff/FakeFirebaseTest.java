package edu.gatech.cs2340.wheresmystuff;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs2340.wheresmystuff.model.FakeFirebase;

/**
 * Tests methods of the FakeFirebase class
 */
public class FakeFirebaseTest {

    private FakeFirebase fakeFirebase;

    public FakeFirebaseTest() {
    }

    /**
     * Creating an instance of FakeFirebase for the tests
     */
    @Before
    public void setUp() {
        fakeFirebase = FakeFirebase.getInstance();
    }

    /**
     * Testing the register method
     */
    @Test
    public void testRegister() {
        //Checking if userList is empty
        Assert.assertEquals("Userlist size is incorrect", 0L,
                (long) fakeFirebase.getUserList().size());

        //Trying to add a null email and password. Should return False
        Assert.assertFalse("Null email or password added", fakeFirebase.register(null, null));
        //Checking that userList is empty
        Assert.assertEquals("UserList size is incorrect", 0L,
                (long) fakeFirebase.getUserList().size());

        //Trying to add an invalid email. Should return False
        Assert.assertFalse("Invalid email added",
                fakeFirebase.register("invalidEmail", "password"));
        //Checking that userList is empty
        Assert.assertEquals("UserList size is incorrect", 0L,
                (long) fakeFirebase.getUserList().size());

        //Trying to add a password that is too short. Should return False
        Assert.assertFalse("Invalid password added",
                fakeFirebase.register("validEmail@gmail.com", "passwor"));
        //Checking that userList is empty
        Assert.assertEquals("UserList size is incorrect", 0L,
                (long) fakeFirebase.getUserList().size());

        //Trying to add a user with a valid email and password. Should return True
        Assert.assertTrue("Register returned false",
                fakeFirebase.register("example@gmail.com", "password"));
        //Checking that userList is the right size and contains user
        Assert.assertEquals("UserList size is incorrect", 1L,
                (long) fakeFirebase.getUserList().size());
        Assert.assertTrue("userList does not contain user",
                fakeFirebase.getUserList().containsKey("example@gmail.com"));

        //Trying to register a user with the same email. Should return False
        Assert.assertFalse("Duplicate email added",
                fakeFirebase.register("example@gmail.com", "password"));
        Assert.assertEquals("UserList size is incorrect", 1L,
                (long) fakeFirebase.getUserList().size());

        //Trying to register another valid user. Should return True
        Assert.assertTrue("Register returned false",
                fakeFirebase.register("example2@gmail.com", "password"));

        //Checking that userList is the right size and contains user
        Assert.assertEquals("UserList size is incorrect", 2L,
                (long) fakeFirebase.getUserList().size());
        Assert.assertTrue("userList does not contain user",
                fakeFirebase.getUserList().containsKey("example2@gmail.com"));
    }
}