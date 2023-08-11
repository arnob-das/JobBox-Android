package com.example.jobbox.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jobbox.R;
import com.example.jobbox.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobDetsils extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detsils);

        Intent intent = getIntent();
        String jobItemId = intent.getStringExtra("jobId");

        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(jobItemId);

        TextView jobPositionText = findViewById(R.id.jobPositionText);

        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Job job = dataSnapshot.getValue(Job.class);

                    jobPositionText.setText(job.getJobPosition());

                } else {
                    // Job with the specified ID doesn't exist
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }
}
