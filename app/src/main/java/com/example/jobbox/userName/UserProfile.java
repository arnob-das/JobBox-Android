package com.example.jobbox.userName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobbox.R;
import com.example.jobbox.navigationDrawer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserProfile extends AppCompatActivity {

    EditText editTextDisplayName, photoUrlEditText;
    Button submitProfileBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        editTextDisplayName = findViewById(R.id.displayNameEditText);
        photoUrlEditText = findViewById(R.id.photoUrlEditText);
        submitProfileBtn = findViewById(R.id.submitDisplayNameBtnId);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            Uri photoUrl = user.getPhotoUrl();

            if (displayName != null && !displayName.isEmpty()) {
                editTextDisplayName.setHint(displayName);
            }
            if (photoUrl != null && !photoUrl.toString().isEmpty()) {
                photoUrlEditText.setHint(photoUrl.toString());
            }
        }

        // Add TextWatcher to monitor EditText changes
        editTextDisplayName.addTextChangedListener(profileTextWatcher);
        photoUrlEditText.addTextChangedListener(profileTextWatcher);

        submitProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        // Initially, disable the submit button
        submitProfileBtn.setEnabled(false);
    }

    // TextWatcher to enable/disable the submit button based on EditText content
    private TextWatcher profileTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            String displayName = editTextDisplayName.getText().toString().trim();
            String photoUrl = photoUrlEditText.getText().toString().trim();

            // Enable the submit button if both fields are not empty, otherwise disable it
            submitProfileBtn.setEnabled(!displayName.isEmpty() && !photoUrl.isEmpty());
        }
    };

    private void updateProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String displayName = editTextDisplayName.getText().toString().trim();
            String photoUrl = photoUrlEditText.getText().toString().trim();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(photoUrl.isEmpty() ? null : Uri.parse(photoUrl))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            navigateToNavigationDrawer();
                        } else {
                            Toast.makeText(UserProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void navigateToNavigationDrawer() {
        Intent intent = new Intent(UserProfile.this, navigationDrawer.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity to prevent going back to it from the NavigationDrawer
    }
}
