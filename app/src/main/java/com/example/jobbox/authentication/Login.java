package com.example.jobbox.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbox.R;
import com.example.jobbox.homePage.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signUpLink;

    FirebaseAuth mAuth;

    private void navigateToActivity() {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUpLink = findViewById(R.id.signUpLink);

        // underline text
        signUpLink.setPaintFlags(signUpLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // initialize firebase
        mAuth = FirebaseAuth.getInstance();

        // sign in with email password
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;

                EditText emailText = findViewById(R.id.email);
                EditText passwordText = findViewById(R.id.password);

                email = String.valueOf(emailText.getText());
                password = String.valueOf(passwordText.getText());

                // check email is empty or not
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                // check password is empty or not
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // connect firebase authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    System.out.println(user.getEmail().toString());
                                    Intent intent = new Intent(Login.this, Home.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // navigate to sign up page
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity();
            }
        });
    }
}