package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.collegescheduler.databinding.ActivityUpdateExamBinding;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.List;

public class UpdateExamActivity extends AppCompatActivity {

    private ActivityUpdateExamBinding binding;
    private ExamsDatabaseHelper db;
    private int examId = -1;

    TextView updateSelectedExamDate;
    Button updatePickExamDate;

    TextView updateSelectedExamCourse; //*
    Button updatePickExamCourse; //*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updatePickExamDate = findViewById(R.id.updatePickExamDate);
        updateSelectedExamDate = findViewById(R.id.updateSelectedExamDate);

        updatePickExamCourse = findViewById(R.id.updatePickExamCourse); //*
        updateSelectedExamCourse = findViewById(R.id.updateSelectedExamCourse); //*

        binding.updatePickExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateExamActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.updateSelectedExamDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.updatePickExamCourse.setOnClickListener(new View.OnClickListener() { //*
            @Override
            public void onClick(View view) {
                ClassesDatabaseHelper classDB = new ClassesDatabaseHelper(UpdateExamActivity.this);
                List<ClassModel> classList = classDB.getAllClasses();
                String[] classes = new String[classList.size() + 1];
                if (classList.size() == 0) {
                    classes = new String[]{"You have no classes. Add a class first."};
                } else {
                    for (int i = 0; i < classList.size(); i++) {
                        classes[i] = classList.get(i).getTitle();
                    }
                    classes[classList.size()] = "No Class";
                }

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateExamActivity.this);
                builder.setTitle("Pick Course");
                String[] finalClasses = classes;
                builder.setItems(finalClasses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < finalClasses.length; i++) {
                            if (i == which) {
                                if (finalClasses[i].equals("You have no classes. Add a class first.")
                                        || finalClasses[i].equals("No Class")) {
                                    binding.updateSelectedExamCourse.setText("________________");
                                    break;
                                }
                                binding.updateSelectedExamCourse.setText(finalClasses[i]);
                                break;
                            }
                        }
                    }
                });
                builder.show();
            }
        });

        db = new ExamsDatabaseHelper(this);

        examId = getIntent().getIntExtra("id", -1);
        if (examId == -1) {
            finish();
        }

        ExamModel examModel = db.getExamByID(examId);
        if (examModel != null) {
            binding.updateTitleExamEditText.setText(examModel.getTitle());

            if (examModel.getDate().equals("")) {
                binding.updateSelectedExamDate.setText("__-__-____");
            } else {
                binding.updateSelectedExamDate.setText(examModel.getDate());
            }

            if (examModel.getCourse().equals("")) { //*
                binding.updateSelectedExamCourse.setText("________________"); //*
            } else { //*
                binding.updateSelectedExamCourse.setText(examModel.getCourse()); //*
            }

            //binding.updateExamCourseEditText.setText(examModel.getCourse());
            setExamTimeRange(examModel.getTime());
            binding.updateLocationExamEditText.setText(examModel.getLocation());
        }

        binding.updateSaveExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmExamUpdateDialog();
            }
        });
    }

    private void handleUpdateExamAction() {

        String date = (String) updateSelectedExamDate.getText();
        if (date.equals("__-__-____")) {
            updateSelectedExamDate.setText("");
        }

        String course = (String) updateSelectedExamCourse.getText(); //*
        if (course.equals("________________")) { //*
            updateSelectedExamCourse.setText(""); //*
        }

        String startTime = binding.updateStartTimeExamEditText.getText().toString().trim();
        String startSuffix = binding.updateAMexam.isChecked() ? " AM" : " PM";
        String endTime = binding.updateEndTimeExamEditText.getText().toString().trim();
        String endSuffix = binding.updateamexam.isChecked() ? " AM" : " PM";

        String timeRange;

        if (startTime.equals("") && endTime.equals("")) {
            timeRange = "";
        } else {
            timeRange = startTime + startSuffix + " - " + endTime + endSuffix;
        }

        db.updateExam(examId, //*
                binding.updateTitleExamEditText.getText().toString().trim(),
                binding.updateSelectedExamDate.getText().toString().trim(),
                binding.updateSelectedExamCourse.getText().toString().trim(),
                timeRange,
                binding.updateLocationExamEditText.getText().toString().trim());
//        db.updateExam(examId,
//                binding.updateTitleExamEditText.getText().toString().trim(),
//                binding.updateSelectedExamDate.getText().toString().trim(),
//                binding.updateExamCourseEditText.getText().toString().trim(),
//                timeRange,
//                binding.updateLocationExamEditText.getText().toString().trim());

        finish();
    }

    private void setExamTimeRange(String timeRange) {

        if (timeRange.equals("")) {
            return;
        }

        if (timeRange.contains("-")) {
            String[] times = timeRange.split(" - ");
            String startTime = times[0];
            String endTime = times[1];

            String[] startTimeParts = startTime.split(" ");
            String startTimeNum = startTimeParts[0];

            String[] endTimeParts = endTime.split(" ");
            String endTimeNum = endTimeParts[0];

            binding.updateStartTimeExamEditText.setText(startTimeNum);
            if (startTime.endsWith("PM")) {
                binding.updatePMexam.setChecked(true);
            } else {
                binding.updateAMexam.setChecked(true);
            }

            binding.updateEndTimeExamEditText.setText(endTimeNum);
            if (endTime.endsWith("PM")) {
                binding.updatepmexam.setChecked(true);
            } else {
                binding.updateamexam.setChecked(true);
            }
        }

    }

    void confirmExamUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + binding.updateTitleExamEditText.getText().toString().trim() + " ?");
        builder.setMessage("Are you sure you want to update this exam?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handleUpdateExamAction(); // Proceed with updating the exam if "Yes" is clicked
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing if "No" is clicked
                finish();
                // delete/comment out line above if you want to stay on the same edit/update screen
                // or keep line if you want to go back to the fragment screen, leaving the edit/update screen
            }
        });
        builder.create().show();
    }

}