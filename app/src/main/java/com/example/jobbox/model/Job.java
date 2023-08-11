package com.example.jobbox.model;

public class Job {
        private String jobPosition;
        private String jobLocation;
        private String company;
        private String description;
        private boolean onSite;
        private boolean remote;
        private boolean fullTime;
        private boolean partTime;
        private boolean intern;
        private String salary;
        String jobPostBy;

    private String jobId; // Add this field

    // Other fields, constructors, getters and setters



        // Empty constructor (required by Firebase)
        public Job() {}

        // Constructor to initialize the job details
        public Job(String jobPosition,String salary, String jobLocation, String company, String description,
                   boolean onSite, boolean remote, boolean fullTime, boolean partTime, boolean intern,String jobPostBy) {
            this.jobPosition = jobPosition;
            this.salary=salary;
            this.jobLocation = jobLocation;
            this.company = company;
            this.description = description;
            this.onSite = onSite;
            this.remote = remote;
            this.fullTime = fullTime;
            this.partTime = partTime;
            this.intern = intern;
            this.jobPostBy=jobPostBy;
        }

        // Getters and Setters for all properties
        public String getJobPosition() {
            return jobPosition;
        }

        public void setJobPosition(String jobPosition) {
            this.jobPosition = jobPosition;
        }

        public String getSalary(){return salary;}

        public void setSalary(String salary){this.salary=salary;}

        public String getJobLocation() {
            return jobLocation;
        }

        public void setJobLocation(String jobLocation) {
            this.jobLocation = jobLocation;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isOnSite() {
            return onSite;
        }

        public void setOnSite(boolean onSite) {
            this.onSite = onSite;
        }

        public boolean isRemote() {
            return remote;
        }

        public void setRemote(boolean remote) {
            this.remote = remote;
        }

        public boolean isFullTime() {
            return fullTime;
        }

        public void setFullTime(boolean fullTime) {
            this.fullTime = fullTime;
        }

        public boolean isPartTime() {
            return partTime;
        }

        public void setPartTime(boolean partTime) {
            this.partTime = partTime;
        }

        public boolean isIntern() {
            return intern;
        }

        public void setIntern(boolean intern) {
            this.intern = intern;
        }

        public String getJobPostBy(){return jobPostBy;}
        public void setJobPostBy(String jobPostBy){this.jobPostBy=jobPostBy;}

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

}
