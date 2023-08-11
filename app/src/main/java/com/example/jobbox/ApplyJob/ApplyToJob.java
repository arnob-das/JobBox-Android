package com.example.jobbox.ApplyJob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobbox.R;
import com.example.jobbox.model.JobApplicant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplyToJob extends AppCompatActivity {

    EditText editTextCvResumeLink;
    Button jobApplyNowButton;
    FirebaseAuth mAuth;
    private DatabaseReference jobsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_to_job);

        mAuth = FirebaseAuth.getInstance();

        editTextCvResumeLink = findViewById(R.id.cvResumeEditTextId);
        jobApplyNowButton=findViewById(R.id.jobApplyNowButtonId);

        FirebaseUser user = mAuth.getCurrentUser();

        Intent intent = getIntent();
        if (user!=null && intent != null) {
            // job id
            String jobId = intent.getStringExtra("jobId");

            jobsRef = FirebaseDatabase.getInstance().getReference("jobs").child(jobId);

            // current user's info
            String displayName = user.getDisplayName();
            String email = user.getEmail();
            String photoUrl = String.valueOf(user.getPhotoUrl());
            String cvResumeLink = editTextCvResumeLink.getText().toString().trim();

            JobApplicant applicant = new JobApplicant(displayName,email,photoUrl,cvResumeLink);


            // apply now button functionality
            jobApplyNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // push the applicant object in selected job's child which is "applicants"
                    if (jobsRef != null) {
                        jobsRef.child("applicants").push().setValue(applicant);
                        Toast.makeText(ApplyToJob.this, "Application submitted successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ApplyToJob.this, "Application Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}