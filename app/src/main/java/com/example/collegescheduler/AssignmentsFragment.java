package com.example.collegescheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.collegescheduler.databinding.FragmentAssignmentsBinding;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Collections;

public class AssignmentsFragment extends Fragment {

    private FragmentAssignmentsBinding binding;
    AssignmentsDatabaseHelper db;
    AssignmentsAdapter assignmentsAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new AssignmentsDatabaseHelper(getContext());
        assignmentsAdapter = new AssignmentsAdapter(getContext(), db.getAllAssignments());

        binding.assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.assignmentsRecyclerView.setAdapter(assignmentsAdapter);

        binding.addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAssignmentActivity.class);
                startActivity(intent);
            }
        });

        binding.sortLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortOptionsDialog();
            }
        });

        return root;

        // return inflater.inflate(R.layout.fragment_assignments, container, false);
    }

    public void onResume() {
        super.onResume();
        assignmentsAdapter.refreshAssignmentData(db.getAllAssignments());
    }

    private void showSortOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort By");
        builder.setItems(new String[]{"Show All Assignments (Original)", "Due Date", "Course"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle sorting options selection
                switch (which) {
                    case 0:
                        assignmentsAdapter.showAllAssignments();
                        break;
                    case 1:
                        assignmentsAdapter.sortByDueDate();
                        break;
                    case 2:
                        assignmentsAdapter.sortByCourse();
                        break;
                }
            }
        });
        builder.show();
    }


}