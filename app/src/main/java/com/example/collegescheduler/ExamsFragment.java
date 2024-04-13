package com.example.collegescheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.collegescheduler.databinding.FragmentExamsBinding;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ExamsFragment extends Fragment {

    private FragmentExamsBinding binding;
    ExamsDatabaseHelper db;
    ExamsAdapter examsAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new ExamsDatabaseHelper(getContext());
        examsAdapter = new ExamsAdapter(getContext(), db.getAllExams());

        binding.examsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.examsRecyclerView.setAdapter(examsAdapter);

        binding.addExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExamActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void onResume() {
        super.onResume();
        examsAdapter.refreshExamData(db.getAllExams());
    }

}