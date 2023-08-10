package com.example.jobbox.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jobbox.R;

public class JobDetsils extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detsils);

        // Retrieve data from Intent extras
        Intent intent = getIntent();
        String jobId = intent.getStringExtra("jobId");
        String jobTitle = intent.getStringExtra("jobTitle");

        // Use the retrieved data as needed
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(jobTitle);
    }
}