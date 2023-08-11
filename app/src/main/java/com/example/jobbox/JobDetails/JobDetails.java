package com.example.jobbox.JobDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.jobbox.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobDetails extends AppCompatActivity {

    private EditText jobPositionEditText;
    private CheckBox onSiteCheckBox;
    private CheckBox remoteCheckBox;
    private EditText jobLocationEditText;
    private EditText companyEditText;
    private CheckBox fullTimeCheckBox;
    private CheckBox partTimeCheckBox;
    private CheckBox internCheckBox;
    private EditText descriptionEditText;
    private EditText salaryEditText;

    private DatabaseReference jobsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        String jobId;

        jobPositionEditText = findViewById(R.id.editTextText);
        onSiteCheckBox = findViewById(R.id.checkBox2);
        remoteCheckBox = findViewById(R.id.checkBox3);
        jobLocationEditText = findViewById(R.id.jobLocation);
        companyEditText = findViewById(R.id.company);
        fullTimeCheckBox = findViewById(R.id.fullTime);
        partTimeCheckBox = findViewById(R.id.partTime);
        internCheckBox = findViewById(R.id.intern);
        descriptionEditText = findViewById(R.id.description);
        salaryEditText = findViewById(R.id.salary);

        Intent intent = getIntent();
        if (intent != null) {
            jobId = intent.getStringExtra("jobId");
            jobsRef = FirebaseDatabase.getInstance().getReference("jobs").child(jobId);


            jobsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve data from the dataSnapshot
                        String jobPosition = dataSnapshot.child("jobPosition").getValue(String.class);
                        boolean isOnSite = dataSnapshot.child("onSite").getValue(Boolean.class);
                        boolean isRemote = dataSnapshot.child("remote").getValue(Boolean.class);
                        String jobLocation = dataSnapshot.child("jobLocation").getValue(String.class);
                        String company = dataSnapshot.child("company").getValue(String.class);
                        boolean isFullTime = dataSnapshot.child("fullTime").getValue(Boolean.class);
                        boolean isPartTime = dataSnapshot.child("partTime").getValue(Boolean.class);
                        boolean isIntern = dataSnapshot.child("intern").getValue(Boolean.class);
                        String description = dataSnapshot.child("description").getValue(String.class);
                        String salary = dataSnapshot.child("salary").getValue(String.class);

                        // Set the retrieved data to the corresponding views
                        jobPositionEditText.setText(jobPosition);
                        onSiteCheckBox.setChecked(isOnSite);
                        remoteCheckBox.setChecked(isRemote);
                        jobLocationEditText.setText(jobLocation);
                        companyEditText.setText(company);
                        fullTimeCheckBox.setChecked(isFullTime);
                        partTimeCheckBox.setChecked(isPartTime);
                        internCheckBox.setChecked(isIntern);
                        descriptionEditText.setText(description);
                        salaryEditText.setText(salary);

                        // Make EditText views non-editable
                        jobPositionEditText.setEnabled(false);
                        jobLocationEditText.setEnabled(false);
                        companyEditText.setEnabled(false);
                        descriptionEditText.setEnabled(false);
                        salaryEditText.setEnabled(false);

                        // Make CheckBox views not clickable
                        onSiteCheckBox.setClickable(false);
                        remoteCheckBox.setClickable(false);
                        fullTimeCheckBox.setClickable(false);
                        partTimeCheckBox.setClickable(false);
                        internCheckBox.setClickable(false);

                    } else {
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors that occur during the database query
                }
            });
        }
    }
}