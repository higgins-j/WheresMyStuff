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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.wheresmystuff.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private View mProgressView;
    private View mLoginFormView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mEmailView = (EditText) findViewById(R.id.email);
        final EditText mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin(mEmailView, mPasswordView);
                    return true;
                }
                return false;
            }
        });

        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(mEmailView, mPasswordView);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(final EditText emailView, final EditText passwordView) {
        emailView.setError(null);
        passwordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (isPasswordInvalid(passwordView)) {
            focusView = passwordView;
            cancel = true;
        }
        if (isEmailInvalid(emailView)) {
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        if (!cancel) {
            // Show a progress spinner, and start background call to Firebase to sign in
            showProgress(true);

            mAuth.signInWithEmailAndPassword(
                    emailView.getText().toString(),
                    passwordView.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("FirebaseSignUp", "signInWithEmail:Success");

                                Intent i = new Intent(getApplicationContext(), App.class);
                                finish();
                                startActivity(i);
                            } else {
                                emailView.setError(getString(R.string.error_email_password_match));
                                passwordView.setError(getString(R.string.error_email_password_match));
                                passwordView.requestFocus();
                                showProgress(false);
                            }
                        }
                    });
        }
    }

    /**
     * Checks if the email in an EditText is valid
     *
     * @param emailView the emailView to check
     * @return if the email is invalid or not
     */
    private boolean isEmailInvalid(EditText emailView) {
        String email = emailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            return true;
        } else if (!email.contains("@")) {
            emailView.setError(getString(R.string.error_invalid_email));
            return true;
        } else return false;
        //TODO: Add more thorough email verification technique
    }

    /**
     * Checks if the password in an EditText is valid
     *
     * @param passwordView the EditText to check
     * @return if the password is invalid or not
     */
    private boolean isPasswordInvalid(EditText passwordView) {
        String password = passwordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            return true;
        } else if (password.length() < 7) {
            passwordView.setError(getString(R.string.error_invalid_password));
            return true;
        } else return false;
        //TODO: Add more thorough password requirement
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

