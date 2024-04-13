package com.example.collegescheduler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentViewHolder> {

    private Context context;
    private List<AssignmentModel> assignmentList;

    private AssignmentsDatabaseHelper db;

    AssignmentsAdapter(Context context, List<AssignmentModel> assignmentList) {
        this.context = context;
        this.assignmentList = assignmentList;
        db = new AssignmentsDatabaseHelper(context);
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder {

        TextView titleAssignmentTextView, dueDateTextView, courseTextView, descriptionTextView;
        ImageView updateAssignmentButton, deleteAssignmentButton;
        // CheckBox deleteAssignmentButton;

        AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            titleAssignmentTextView = itemView.findViewById(R.id.titleAssignmentTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            courseTextView = itemView.findViewById(R.id.courseTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

            updateAssignmentButton = itemView.findViewById(R.id.updateAssignmentButton);

            deleteAssignmentButton = itemView.findViewById(R.id.deleteAssignmentButton);
        }

    }

    @NonNull
    @Override
    public AssignmentsAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.assignment_item, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentsAdapter.AssignmentViewHolder holder, int position) {
        AssignmentModel assignmentModel = assignmentList.get(position);
        if (assignmentModel != null) {
            holder.titleAssignmentTextView.setText(assignmentModel.getTitle());

            String date = (String) holder.dueDateTextView.getText();
            if (date.equals("__-__-____")) {
                holder.dueDateTextView.setText("N/A");
            } else {
                holder.dueDateTextView.setText(assignmentModel.getDueDate());
            }


            holder.courseTextView.setText(assignmentModel.getCourse());
            holder.descriptionTextView.setText(assignmentModel.getDescription());

        }

        holder.updateAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAssignmentActivity.class);
                intent.putExtra("id", assignmentModel.getId());
                context.startActivity(intent);
            }
        });

        holder.deleteAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                db.deleteAssignment(assignmentModel.getId());
//                refreshAssignmentData(db.getAllAssignments());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete " + holder.titleAssignmentTextView.getText().toString().trim() + " ?");
                builder.setMessage("Are you sure you want to delete this assignment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteAssignment(assignmentModel.getId()); // Proceed with deleting the assignment if "Yes" is clicked
                        refreshAssignmentData(db.getAllAssignments());
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
        return assignmentList.size();
    }

    void refreshAssignmentData(List<AssignmentModel> updatedList) {
        assignmentList.clear();
        assignmentList.addAll(updatedList);
        notifyDataSetChanged();
    }

    public void showAllAssignments() {
        assignmentList.clear();
        assignmentList.addAll(db.getAllAssignments());
        notifyDataSetChanged();
    }

    public void sortByDueDate() {
        Collections.sort(assignmentList, new Comparator<AssignmentModel>() {
            @Override
            public int compare(AssignmentModel assignment1, AssignmentModel assignment2) {
                // return assignment1.getDueDate().compareTo(assignment2.getDueDate());
                DateFormat dateFormat = new SimpleDateFormat("M-dd-yyyy");
                DateFormat longDateFormat = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    Date dueDate1, dueDate2;
                    if (assignment1.getDueDate().indexOf('-') == 1) {
                        dueDate1 = dateFormat.parse(assignment1.getDueDate());
                    } else {
                        dueDate1 = longDateFormat.parse(assignment1.getDueDate());
                    }
                    if (assignment2.getDueDate().indexOf('-') == 1) {
                        dueDate2 = dateFormat.parse(assignment2.getDueDate());
                    } else {
                        dueDate2 = longDateFormat.parse(assignment2.getDueDate());
                    }
                    return dueDate1.compareTo(dueDate2);
                } catch (ParseException e) {
                    return assignment1.getDueDate().compareTo(assignment2.getDueDate());
                }
            }
        });
        notifyDataSetChanged();
    }

    public void sortByCourse() {
        Collections.sort(assignmentList, new Comparator<AssignmentModel>() {
            @Override
            public int compare(AssignmentModel assignment1, AssignmentModel assignment2) {
                return assignment1.getCourse().compareTo(assignment2.getCourse());
            }
        });
        notifyDataSetChanged();
    }

}