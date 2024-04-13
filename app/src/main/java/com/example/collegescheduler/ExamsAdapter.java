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

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamViewHolder> {

    private Context context;
    private List<ExamModel> examList;

    private ExamsDatabaseHelper db;

    ExamsAdapter(Context context, List<ExamModel> examList) {
        this.context = context;
        this.examList = examList;
        db = new ExamsDatabaseHelper(context);
    }

    class ExamViewHolder extends RecyclerView.ViewHolder {

        TextView titleExamTextView, examDateTextView, examCourseTextView, examTimeTextView, examLocationTextView;
        ImageView updateExamButton, deleteExamButton;

        ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            titleExamTextView = itemView.findViewById(R.id.titleExamTextView);
            examDateTextView = itemView.findViewById(R.id.examDateTextView);
            examCourseTextView = itemView.findViewById(R.id.examCourseTextView);
            examTimeTextView = itemView.findViewById(R.id.examTimeTextView);
            examLocationTextView = itemView.findViewById(R.id.examLocationTextView);

            updateExamButton = itemView.findViewById(R.id.updateExamButton);

            deleteExamButton = itemView.findViewById(R.id.deleteExamButton);
        }

    }

    @NonNull
    @Override
    public ExamsAdapter.ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exam_item, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamsAdapter.ExamViewHolder holder, int position) {
        ExamModel examModel = examList.get(position);
        holder.titleExamTextView.setText(examModel.getTitle());
        holder.examDateTextView.setText(examModel.getDate());
        holder.examCourseTextView.setText(examModel.getCourse());
        holder.examTimeTextView.setText(examModel.getTime());
        holder.examLocationTextView.setText(examModel.getLocation());

        holder.updateExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateExamActivity.class);
                intent.putExtra("id", examModel.getId());
                context.startActivity(intent);
            }
        });

        holder.deleteExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete " + holder.titleExamTextView.getText().toString().trim() + " ?");
                builder.setMessage("Are you sure you want to delete this exam?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteExam(examModel.getId()); // Proceed with deleting the exam if "Yes" is clicked
                        refreshExamData(db.getAllExams());
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
        return examList.size();
    }

    void refreshExamData(List<ExamModel> updatedList) {
        examList.clear();
        examList.addAll(updatedList);
        notifyDataSetChanged();
    }

}
