package com.example.jobbox.homePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbox.R;
import com.example.jobbox.authentication.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    FirebaseAuth mAuth;
    private static final String TAG = "HomeActivity";

    Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView textView3 = findViewById(R.id.textView3);
        logoutButton=findViewById(R.id.logoutButton);

        mAuth = FirebaseAuth.getInstance();

        // Get the current user's email
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String name = user.getDisplayName();
            Uri photoUrl = user.getPhotoUrl();

            textView3.setText(email + " "+name);
            Log.d(TAG, "User object: " + user.toString());
        }

        // logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sign out user
                mAuth.signOut();

                Toast.makeText(Home.this, "Logout Successful", Toast.LENGTH_SHORT).show();

                // navigate to login page
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);

                finish();
            }
        });

    }
}