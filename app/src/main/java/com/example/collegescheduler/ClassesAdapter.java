package com.example.collegescheduler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassViewHolder> {

    private Context context;
    private List<ClassModel> classList;

    private ClassesDatabaseHelper db;

    ClassesAdapter(Context context, List<ClassModel> classList) {
        this.context = context;
        this.classList = classList;
        db = new ClassesDatabaseHelper(context);
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, coursenameTextView, daysTextView, timeTextView, locationTextView, instructorTextView;
        ImageView updateButton, deleteButton;

        ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            coursenameTextView = itemView.findViewById(R.id.coursenameTextView);
            daysTextView = itemView.findViewById(R.id.daysTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            instructorTextView = itemView.findViewById(R.id.instructorTextView);

            updateButton = itemView.findViewById(R.id.updateButton);

            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

    }

    @NonNull
    @Override
    public ClassesAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.class_item, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesAdapter.ClassViewHolder holder, int position) {
        ClassModel classModel = classList.get(position);
        holder.titleTextView.setText(classModel.getTitle());
        holder.coursenameTextView.setText(classModel.getCourseName());
        holder.daysTextView.setText(classModel.getDays());
        holder.timeTextView.setText(classModel.getTime());
        holder.locationTextView.setText(classModel.getLocation());
        holder.instructorTextView.setText(classModel.getInstructor());

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateClassActivity.class);
                intent.putExtra("id", classModel.getId());
                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete " + holder.titleTextView.getText().toString().trim() + " ?");
                builder.setMessage("Are you sure you want to delete this class?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteClass(classModel.getId()); // Proceed with deleting the class if "Yes" is clicked
                        refreshData(db.getAllClasses());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing if "No" is clicked
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    void refreshData(List<ClassModel> updatedList) {
        classList.clear();
        classList.addAll(updatedList);
        notifyDataSetChanged();
    }

}