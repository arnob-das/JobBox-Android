package com.example.jobbox.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobbox.adapter.JobItemAdapter;
import com.example.jobbox.databinding.FragmentJobsBinding;
import com.example.jobbox.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchJobFragment extends Fragment {

    private FragmentJobsBinding binding;

    private RecyclerView recyclerView;
    DatabaseReference database;
    private JobItemAdapter jobItemAdapter;
    ArrayList<Job> jobArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentJobsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.jobsList;
        database = FirebaseDatabase.getInstance().getReference("jobs");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        jobArrayList = new ArrayList<>();

        jobItemAdapter = new JobItemAdapter(requireContext(), jobArrayList);
        recyclerView.setAdapter(jobItemAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Job job = dataSnapshot.getValue(Job.class);
                    job.setJobId(dataSnapshot.getKey()); // Set the job ID from the Firebase key
                    jobArrayList.add(job);
                }
                jobItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}