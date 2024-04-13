package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Button;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import com.example.collegescheduler.databinding.ActivityUpdateTaskBinding;

public class UpdateTaskActivity extends AppCompatActivity {

    private ActivityUpdateTaskBinding binding;
    private TasksDatabaseHelper db;
    private int taskId = -1;

    TextView updateSelectedTaskDueDate;
    Button updatePickTaskDueDate;
//    int numDayOfWeek;
//    String stringDayOfWeek = "";
    String selectedPriority = "";

    TextView updateSelectedTaskCourse; //*
    Button updatePickTaskCourse; //*


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updatePickTaskDueDate = findViewById(R.id.updatePickTaskDueDate);
        updateSelectedTaskDueDate = findViewById(R.id.updateSelectedTaskDueDate);

        updatePickTaskCourse = findViewById(R.id.updatePickTaskCourse); //*
        updateSelectedTaskCourse = findViewById(R.id.updateSelectedTaskCourse); //*

        binding.updatePickTaskDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                //numDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.updateSelectedTaskDueDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.updatePickTaskCourse.setOnClickListener(new View.OnClickListener() { //*
            @Override
            public void onClick(View view) {
                ClassesDatabaseHelper classDB = new ClassesDatabaseHelper(UpdateTaskActivity.this);
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

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Pick Course");
                String[] finalClasses = classes;
                builder.setItems(finalClasses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < finalClasses.length; i++) {
                            if (i == which) {
                                if (finalClasses[i].equals("You have no classes. Add a class first.")
                                        || finalClasses[i].equals("No Class")) {
                                    binding.updateSelectedTaskCourse.setText("________________");
                                    break;
                                }
                                binding.updateSelectedTaskCourse.setText(finalClasses[i]);
                                break;
                            }
                        }
                    }
                });
                builder.show();
            }
        });

        db = new TasksDatabaseHelper(this);

        taskId = getIntent().getIntExtra("id", -1);
        if (taskId == -1) {
            finish();
        }

        TaskModel taskModel = db.getTaskByID(taskId);
        if (taskModel != null) {
            binding.updateTitleTaskEditText.setText(taskModel.getTitle());

            // String date = taskModel.getDueDate();
            if (taskModel.getDueDate().equals("N/A")) {
                binding.updateSelectedTaskDueDate.setText("__-__-____");
            } else {
                binding.updateSelectedTaskDueDate.setText(taskModel.getDueDate());
            }

            if (taskModel.getCourse().equals("")) { //*
                binding.updateSelectedTaskCourse.setText("________________"); //*
            } else { //*
                binding.updateSelectedTaskCourse.setText(taskModel.getCourse()); //*
            }

            //binding.updateCourseTaskEditText.setText(taskModel.getCourse());
            setPriorityCheckboxes(taskModel.getPriority());
            binding.updateMoreInfoEditText.setText(taskModel.getMoreInfo());
        }

        binding.updateSaveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmTaskUpdateDialog();
            }
        });
    }

    private void setPriorityCheckboxes(String priority) {
        if (priority.equals("Urgent")) {
            binding.Urgent.setChecked(true);
        } else if (priority.equals("High")) {
            binding.High.setChecked(true);
        } else if (priority.equals("Medium")) {
            binding.Medium.setChecked(true);
        } else if (priority.equals("Low")) {
            binding.Low.setChecked(true);
        }
    }


    void confirmTaskUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + binding.updateTitleTaskEditText.getText().toString().trim() + " ?");
        builder.setMessage("Are you sure you want to update this task?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handleUpdateTaskAction(); // Proceed with updating the task if "Yes" is clicked
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


    private void handleUpdateTaskAction() {
//        String theDate = (String) binding.updateSelectedTaskDueDate.getText();
//        if (!(theDate.equals("__-__-____"))) {
//            switch (numDayOfWeek) {
//                case 1:
//                    stringDayOfWeek = "Sun";
//                    break;
//                case 2:
//                    stringDayOfWeek = "Mon";
//                    break;
//                case 3:
//                    stringDayOfWeek = "Tue";
//                    break;
//                case 4:
//                    stringDayOfWeek = "Wed";
//                    break;
//                case 5:
//                    stringDayOfWeek = "Thu";
//                    break;
//                case 6:
//                    stringDayOfWeek = "Fri";
//                    break;
//                case 7:
//                    stringDayOfWeek = "Sat";
//                    break;
//            }
//        } else {
//            stringDayOfWeek = "";
//        }

        if (binding.Urgent.isChecked()) {
            selectedPriority = "Urgent";
        } else if (binding.High.isChecked()) {
            selectedPriority = "High";
        } else if (binding.Medium.isChecked()) {
            selectedPriority = "Medium";
        } else if (binding.Low.isChecked()) {
            selectedPriority = "Low";
        } else {
            selectedPriority = "None";
        }

        String date = (String) updateSelectedTaskDueDate.getText();
        if (date.equals("__-__-____")) {
            updateSelectedTaskDueDate.setText("N/A");
        }

        String course = (String) updateSelectedTaskCourse.getText(); //*
        if (course.equals("________________")) { //*
            updateSelectedTaskCourse.setText(""); //*
        }

//        db.updateTask(taskId,
//                binding.updateTitleTaskEditText.getText().toString().trim(),
//                binding.updateSelectedTaskDueDate.getText().toString().trim(),
//                stringDayOfWeek,
//                binding.updateCourseTaskEditText.getText().toString().trim(),
//                binding.updateMoreInfoEditText.getText().toString().trim(),
//                selectedPriority,
//                "INCOMPLETE");

        db.updateTask(taskId, //*
                binding.updateTitleTaskEditText.getText().toString().trim(),
                binding.updateSelectedTaskDueDate.getText().toString().trim(),
                binding.updateSelectedTaskCourse.getText().toString().trim(),
                binding.updateMoreInfoEditText.getText().toString().trim(),
                selectedPriority,
                "INCOMPLETE");
//        db.updateTask(taskId,
//                binding.updateTitleTaskEditText.getText().toString().trim(),
//                binding.updateSelectedTaskDueDate.getText().toString().trim(),
//                binding.updateCourseTaskEditText.getText().toString().trim(),
//                binding.updateMoreInfoEditText.getText().toString().trim(),
//                selectedPriority,
//                "INCOMPLETE");
        finish();
    }


}