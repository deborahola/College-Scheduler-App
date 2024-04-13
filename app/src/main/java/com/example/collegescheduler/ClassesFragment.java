package com.example.collegescheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.collegescheduler.databinding.FragmentClassesBinding;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;
    ClassesDatabaseHelper db;
    ClassesAdapter classesAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new ClassesDatabaseHelper(getContext());
        classesAdapter = new ClassesAdapter(getContext(), db.getAllClasses());

        binding.classesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.classesRecyclerView.setAdapter(classesAdapter);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddClassActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void onResume() {
        super.onResume();
        classesAdapter.refreshData(db.getAllClasses());
    }

}