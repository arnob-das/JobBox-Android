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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
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
                String email,password;

                EditText emailText = findViewById(R.id.email);
                EditText passwordText = findViewById(R.id.password);

                email = String.valueOf(emailText.getText());
                password = String.valueOf(passwordText.getText());

                // check email is empty or not
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                // check password is empty or not
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                // create user with email password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("Successful");
                                    // Sign up success, update UI with the signed-in user's information
                                    Toast.makeText(SignUp.this, "Sign Up Successful",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("Failed");
                                    // If sign up fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, "Sign Up failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        // gmail sign in
        signUpWithGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        


        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity();
            }
        });
    }
}