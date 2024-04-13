package com.example.collegescheduler;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    CardView classCard;
    CardView assignmentsCard;
    CardView examsCard;
    CardView todolistCard;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        classCard = view.findViewById(R.id.classCard);
        assignmentsCard = view.findViewById(R.id.assignmentsCard);
        examsCard = view.findViewById(R.id.examsCard);
        todolistCard = view.findViewById(R.id.todolistCard);

        classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ClassesFragment()).commit();
            }
        });

        assignmentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AssignmentsFragment()).commit();
            }
        });

        examsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExamsFragment()).commit();
            }
        });

        todolistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ToDoListFragment()).commit();
            }
        });

        return view;

    }
}