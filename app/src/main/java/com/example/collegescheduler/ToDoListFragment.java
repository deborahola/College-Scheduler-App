package com.example.collegescheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.collegescheduler.databinding.FragmentToDoListBinding;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Collections;

public class ToDoListFragment extends Fragment {

    private FragmentToDoListBinding binding;
    TasksDatabaseHelper db;
    TasksAdapter tasksAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentToDoListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new TasksDatabaseHelper(getContext());
        tasksAdapter = new TasksAdapter(getContext(), db.getAllTasks());

        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.tasksRecyclerView.setAdapter(tasksAdapter);

        binding.addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        binding.sortTaskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortOptionsDialog();
            }
        });

        return root;

    }

    public void onResume() {
        super.onResume();
        tasksAdapter.refreshTaskData(db.getAllTasks());
    }

//    private void showSortOptionsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Sort By");
//        //builder.setItems(new String[]{"Due Date", "Course"},...
//        builder.setItems(new String[]{"Due Date", "Course", "Status: Complete/Incomplete"}, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Handle sorting options selection
//                switch (which) {
//                    case 0:
//                        tasksAdapter.sortByDueDate();
//                        break;
//                    case 1:
//                        tasksAdapter.sortByCourse();
//                        break;
//                    case 2:
//                        tasksAdapter.sortByStatus();
//                        break;
//                }
//            }
//        });
//        builder.show();
//    }

    private void showSortOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort By");
        builder.setItems(new String[]{"Show All Tasks (Original)", "Due Date", "Course",
                "Status: Complete/Incomplete", "Priority"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        tasksAdapter.showAllTasks();
                        break;
                    case 1:
                        tasksAdapter.sortByDueDate();
                        break;
                    case 2:
                        tasksAdapter.sortByCourse();
                        break;
                    case 3:
                        showSortOptionsDialogSTATUS();
                        break;
                    case 4:
                        showSortOptionsDialogPRIORITY();
                        break;
                }
            }
        });
        builder.show();
    }

    private void showSortOptionsDialogSTATUS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort By - Status: Complete/Incomplete");
        builder.setItems(new String[]{"Complete", "Incomplete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        tasksAdapter.showOnlyCompleteTasks();
                        break;
                    case 1:
                        tasksAdapter.showOnlyIncompleteTasks();
                        break;
                }
            }
        });
        builder.show();
    }

    private void showSortOptionsDialogPRIORITY() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort By - Priority");
        builder.setItems(new String[]{"Urgent", "High", "Medium", "Low", "None"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        tasksAdapter.showOnlyUrgentTasks();
                        break;
                    case 1:
                        tasksAdapter.showOnlyHighTasks();
                        break;
                    case 2:
                        tasksAdapter.showOnlyMediumTasks();
                        break;
                    case 3:
                        tasksAdapter.showOnlyLowTasks();
                        break;
                    case 4:
                        tasksAdapter.showOnlyNoneTasks();
                        break;
                }
            }
        });
        builder.show();
    }



}