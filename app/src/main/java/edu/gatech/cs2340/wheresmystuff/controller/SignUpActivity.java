package edu.gatech.cs2340.wheresmystuff.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.cs2340.wheresmystuff.R;
import edu.gatech.cs2340.wheresmystuff.model.AccountType;

/**
 * A registration screen that offers sign up via email/password.
 */
public class SignUpActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordViewOne;
    private EditText mPasswordViewTwo;
    private Spinner mSpinner;
    private View mProgressView;
    private View mLoginFormView;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordViewOne = (EditText) findViewById(R.id.password_one);
        mPasswordViewOne.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.signup || id == EditorInfo.IME_NULL) {
                    attemptSignUp();
                    return true;
                }
                return false;
            }
        });

        mPasswordViewTwo = (EditText) findViewById(R.id.password_two);
        mPasswordViewTwo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.signup || id == EditorInfo.IME_NULL) {
                    attemptSignUp();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });

        mSpinner = (Spinner) findViewById(R.id.spinnerUserType);


        ArrayAdapter<AccountType> accountTypeArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, AccountType.values());
        accountTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(accountTypeArrayAdapter);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Attempt to sign up for a new account
     *
     * If there are errors (invalid email, passwords don't match, email already registered)
     * then the progress view is hidden and the form is shown again
     */
    private void attemptSignUp() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordViewOne.setError(null);
        mPasswordViewTwo.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordViewOne.getText().toString();
        final String passwordTwo = mPasswordViewTwo.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordViewOne.setError(getString(R.string.error_field_required));
            focusView = mPasswordViewOne;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordViewOne.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordViewOne;
            cancel = true;
        }

        // Check for matching password
        if (!password.equals(passwordTwo)) {
            mPasswordViewOne.setError("Passwords do not match");
            mPasswordViewTwo.setError("Passwords do not match");
            focusView = mPasswordViewOne;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("FirebaseSignUp", "createUserWithEmail:Success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user == null ? "null" : user.getUid();

                                // TODO: if successful, check if User should be an admin and add
                                //       them to list of admins in Firebase database
                                if (mSpinner.getSelectedItem().equals(AccountType.ADMIN)) {
                                    mDatabase.child("admins").child(uid)
                                            .setValue(email.split("@")[0]);
                                }

                                Intent i = new Intent(getApplicationContext(), App.class);
                                finish();
                                startActivity(i);
                            } else {
                                // Probably means the email was taken
                                mEmailView.setError(getString(R.string.error_email_already_registered));
                                mEmailView.requestFocus();
                                showProgress(false);
                            }
                        }
                    });
        }
    }

    /**
     * A small check to ensure that an email passed in is valid
     *
     * @param email the string that is being checked
     * @return whether the email is valid
     */
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /**
     * A small check to ensure the password meets requirements
     *
     * @param password the string that is being checked
     * @return whether the password is valid
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 7;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
    }
}

