package com.example.jobbox.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbox.R;
import com.example.jobbox.authentication.Login;
import com.example.jobbox.navigationDrawer;
import com.example.jobbox.userName.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    // firebase auth variable
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        TextView textView = findViewById(R.id.underLine);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ImageView splashScreenButton = findViewById(R.id.splashScreenButton);
        splashScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Intent intent;

                if (currentUser == null) {
                    intent = new Intent(SplashScreen.this, Login.class);
                } else if (currentUser.getDisplayName() == null || currentUser.getDisplayName().isEmpty()) {
                    Log.d("userName",currentUser.getDisplayName());
                    intent = new Intent(SplashScreen.this, UserProfile.class);

                } else {
                    intent = new Intent(SplashScreen.this, navigationDrawer.class);
                }

                // Check if intent is not null before starting the activity
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(SplashScreen.this, "Error navigating to the next activity", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}