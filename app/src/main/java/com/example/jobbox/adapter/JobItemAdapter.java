package com.example.jobbox.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jobbox.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobbox.model.Job;
import com.example.jobbox.ui.search.JobDetsils;

import java.util.ArrayList;

public class JobItemAdapter extends RecyclerView.Adapter<JobItemAdapter.JobItemViewHolder> {

    Context context;
    ArrayList<Job> jobsList;

    public JobItemAdapter(Context context, ArrayList<Job> jobsList) {
        this.context = context;
        this.jobsList = jobsList;
    }

    @NonNull
    @Override
    public JobItemAdapter.JobItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.job_item,parent,false);
        return new JobItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JobItemAdapter.JobItemViewHolder holder, int position) {
        Job jobs = jobsList.get(position);

        holder.jobPosition.setText(jobs.getJobPosition());
        holder.company.setText(jobs.getCompany());
        holder.jobLocation.setText(jobs.getJobLocation());
        holder.salary.setText(jobs.getSalary()+" Tk/Month");
        // Set Full Time text based on the boolean value
        if (jobs.isFullTime()) {
            holder.fullTime.setText("Full Time");
        } else {
            holder.fullTime.setVisibility(View.GONE);
        }
        if (jobs.isPartTime()) {
            holder.partTime.setText("Part Time");
        } else {
            holder.partTime.setVisibility(View.GONE);
        }
        if (jobs.isIntern()) {
            holder.intern.setText("Intern");
        } else {
            holder.intern.setVisibility(View.GONE);
        }
        if (jobs.isOnSite()) {
            holder.onSite.setText("On Site");
        } else {
            holder.onSite.setVisibility(View.GONE);
        }
        if (jobs.isRemote()) {
            holder.remote.setText("Remote");
        } else {
            holder.remote.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(context, JobDetsils.class);
                // Pass any relevant data to the new activity using Intent extras
//                intent.putExtra("jobId", jobs.getJobId()); // Replace with your actual field name
//                intent.putExtra("jobTitle", jobs.getTitle()); // Replace with your actual field name

                intent.putExtra("jobId", "Jobs id"); // Replace with your actual field name
                intent.putExtra("jobTitle", "jobs title"); // Replace with your actual field name
                // Start the new activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class JobItemViewHolder extends RecyclerView.ViewHolder{

        TextView jobPosition,company,jobLocation,fullTime,partTime,intern,onSite,remote,salary;

        public JobItemViewHolder(@NonNull View itemView) {
            super(itemView);
            jobPosition=itemView.findViewById(R.id.jobPosition);
            company=itemView.findViewById(R.id.company);
            jobLocation=itemView.findViewById(R.id.jobLocation);
            fullTime=itemView.findViewById(R.id.fullTime);
            partTime=itemView.findViewById(R.id.partTime);
            intern=itemView.findViewById(R.id.intern);
            onSite=itemView.findViewById(R.id.onSite);
            remote=itemView.findViewById(R.id.remote);
            salary=itemView.findViewById(R.id.salary);
        }
    }
}
