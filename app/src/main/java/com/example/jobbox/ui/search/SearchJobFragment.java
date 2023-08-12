package com.example.jobbox.ui.search;

import com.example.jobbox.R;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    private EditText searchBox;

    ArrayList<Job> jobArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentJobsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchBox = root.findViewById(R.id.searchBox);


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

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for your implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterJobs(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for your implementation
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Method to filter jobs based on the search keyword
    private void filterJobs(String keyword) {
        ArrayList<Job> filteredList = new ArrayList<>();
        for (Job job : jobArrayList) {
            if (job.getJobPosition().toLowerCase().contains(keyword)) {
                filteredList.add(job);
            }
        }
        jobItemAdapter.filterList(filteredList);
    }


}