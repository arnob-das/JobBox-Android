package com.example.jobbox.ui.post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jobbox.R;
import com.example.jobbox.databinding.FragmentPostjobBinding;
import com.example.jobbox.model.Job;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class PostFragment extends Fragment {

    private FirebaseAuth mAuth;

    private FragmentPostjobBinding binding;
    private DatabaseReference databaseReference;
    private EditText jobPositionEditText, jobLocationEditText, companyEditText, descriptionEditText,salaryEditText;
    private CheckBox onSiteCheckBox, remoteCheckBox, fullTimeCheckBox, partTimeCheckBox, internCheckBox;
    private Button postJobButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PostViewModel galleryViewModel =
                new ViewModelProvider(this).get(PostViewModel.class);

        binding = FragmentPostjobBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getActivity().setTitle("Post Job");


        // initialize firebase
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("jobs");

        // Initialize views
        jobPositionEditText = root.findViewById(R.id.editTextText);
        jobLocationEditText = root.findViewById(R.id.jobLocation);
        companyEditText = root.findViewById(R.id.company);
        descriptionEditText = root.findViewById(R.id.description);
        onSiteCheckBox = root.findViewById(R.id.checkBox2);
        remoteCheckBox = root.findViewById(R.id.checkBox3);
        fullTimeCheckBox = root.findViewById(R.id.fullTime);
        partTimeCheckBox = root.findViewById(R.id.partTime);
        internCheckBox = root.findViewById(R.id.intern);
        postJobButton = root.findViewById(R.id.postJobBtn);
        salaryEditText=root.findViewById(R.id.salary);

        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get job details from the input fields
                String jobPosition = jobPositionEditText.getText().toString().trim();
                String jobLocation = jobLocationEditText.getText().toString().trim();
                String company = companyEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String salary = salaryEditText.getText().toString().trim();
                boolean isOnSite = onSiteCheckBox.isChecked();
                boolean isRemote = remoteCheckBox.isChecked();
                boolean isFullTime = fullTimeCheckBox.isChecked();
                boolean isPartTime = partTimeCheckBox.isChecked();
                boolean isIntern = internCheckBox.isChecked();

                // Validate the input fields (You can add more validations as needed)
                if (jobPosition.isEmpty() || jobLocation.isEmpty() || company.isEmpty() || description.isEmpty()) {
                    showToast("Please fill in all fields.");
                    return;
                }

                String jobPostBy = currentUser.getEmail();

                // Create a new job object
                Job job = new Job(jobPosition, salary, jobLocation, company, description, isOnSite, isRemote, isFullTime, isPartTime, isIntern,jobPostBy);

                // Generate a unique key for the job post
                String jobId = databaseReference.push().getKey();

                // Use the generated key to store the job in the database
                if (jobId != null) {
                    databaseReference.child(jobId).setValue(job, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                // Success
                                showToast("Job posted successfully!");
                            } else {
                                // Error occurred
                                showToast("Failed to post job: " + error.getMessage());
                            }
                        }
                    });
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
