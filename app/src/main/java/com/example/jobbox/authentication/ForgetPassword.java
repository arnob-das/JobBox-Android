package com.example.jobbox.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobbox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText email;
    Button resetPasswordBtn,backToLoginButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.email);
        resetPasswordBtn=findViewById(R.id.resetPasswordBtn);
        backToLoginButton=findViewById(R.id.backToLoginButton);

        // reset password
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();

                // if email is empty
                if(TextUtils.isEmpty(emailText)){
                    Toast.makeText(ForgetPassword.this, "Enter Email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // initialize firebase
                mAuth = FirebaseAuth.getInstance();

                // send reset password email
                mAuth.sendPasswordResetEmail(emailText)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgetPassword.this, "Reset Password Email Send",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    //  if error occours
                                    Toast.makeText(ForgetPassword.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // navigate to login page
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPassword.this, Login.class);
                startActivity(intent);
            }
        });
    }
}