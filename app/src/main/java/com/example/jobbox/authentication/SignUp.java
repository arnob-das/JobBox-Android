package com.example.jobbox.authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbox.R;
import com.example.jobbox.splashScreen.SplashScreen;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUp extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;


    FirebaseAuth mAuth;

    private void navigateToActivity() {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // configure firebase
        mAuth = FirebaseAuth.getInstance();

        // underline text
        TextView signInLink = findViewById(R.id.signInLink);
        signInLink.setPaintFlags(signInLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button signUpButton = findViewById(R.id.signUpButton);
        Button signUpWithGoogleButton = findViewById(R.id.signUpWithGoogleButton);

        // email password sign up
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;

                EditText emailText = findViewById(R.id.email);
                EditText passwordText = findViewById(R.id.password);

                email = String.valueOf(emailText.getText());
                password = String.valueOf(passwordText.getText());

                // check email is empty or not
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                // check password is empty or not
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                // create user with email password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //send verification link
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(SignUp.this, "Sign Up Successful. Please Verify email.",
                                                        Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(SignUp.this, task.getException().getMessage() ,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    // Sign up success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SignUp.this, Login.class);
                                    startActivity(intent);
                                } else {
                                    // If sign up fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, "Sign Up failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        // Set up Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, /* OnConnectionFailedListener */ null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // sign up with google
        signUpWithGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });



        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity();
            }
        });
    }

    // Method to handle Google Sign-In button click
    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Handle the result of the Google Sign-In intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }

    // Handle the Google Sign-In result
    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Google Sign-In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
            Toast.makeText(SignUp.this, "Google Sign-In Successful.", Toast.LENGTH_SHORT).show();
        } else {
            // Google Sign-In failed
            Toast.makeText(SignUp.this, "Google Sign-In failed.", Toast.LENGTH_SHORT).show();
        }
    }

    // Authenticate with Firebase using the Google account obtained from Google Sign-In
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println(user.getEmail().toString());
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
