package com.example.jobbox.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbox.R;
import com.example.jobbox.authentication.Login;

public class SplashScreen extends AppCompatActivity {

    // Method to navigate to the second activity
    private void navigateToLoginActivity() {
        Intent intent = new Intent(SplashScreen.this, Login.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView textView = findViewById(R.id.underLine);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ImageView splashScreenButton = findViewById(R.id.splashScreenButton);
        splashScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLoginActivity();
            }
        });



    }
}