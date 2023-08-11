package com.example.jobbox.model;

public class JobApplicant {
    private String displayName,email,photoUrl,cvResumeLink;

    public JobApplicant(String displayName, String email, String photoUrl, String cvResumeLink) {
        this.displayName = displayName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.cvResumeLink = cvResumeLink;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCvResumeLink() {
        return cvResumeLink;
    }

    public void setCvResumeLink(String cvResumeLink) {
        this.cvResumeLink = cvResumeLink;
    }
}
