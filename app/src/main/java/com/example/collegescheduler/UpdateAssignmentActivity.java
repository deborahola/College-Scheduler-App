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

import com.example.collegescheduler.databinding.ActivityUpdateAssignmentBinding;

public class UpdateAssignmentActivity extends AppCompatActivity {

    private ActivityUpdateAssignmentBinding binding;
    private AssignmentsDatabaseHelper db;
    private int assignmentId = -1;

    TextView updateSelectedDueDate;
    Button updatePickDueDate;

    TextView updateSelectedCourse; //*
    Button updatePickCourse; //*


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updatePickDueDate = findViewById(R.id.updatePickDueDate);
        updateSelectedDueDate = findViewById(R.id.updateSelectedDueDate);

        updatePickCourse = findViewById(R.id.updatePickCourse); //*
        updateSelectedCourse = findViewById(R.id.updateSelectedCourse); //*

        binding.updatePickDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateAssignmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.updateSelectedDueDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.updatePickCourse.setOnClickListener(new View.OnClickListener() { //*
            @Override
            public void onClick(View view) {
                ClassesDatabaseHelper classDB = new ClassesDatabaseHelper(UpdateAssignmentActivity.this);
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

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateAssignmentActivity.this);
                builder.setTitle("Pick Course");
                String[] finalClasses = classes;
                builder.setItems(finalClasses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < finalClasses.length; i++) {
                            if (i == which) {
                                if (finalClasses[i].equals("You have no classes. Add a class first.")
                                        || finalClasses[i].equals("No Class")) {
                                    binding.updateSelectedCourse.setText("________________");
                                    break;
                                }
                                binding.updateSelectedCourse.setText(finalClasses[i]);
                                break;
                            }
                        }
                    }
                });
                builder.show();
            }
        });

        db = new AssignmentsDatabaseHelper(this);

        assignmentId = getIntent().getIntExtra("id", -1);
        if (assignmentId == -1) {
            finish();
        }

        AssignmentModel assignmentModel = db.getAssignmentByID(assignmentId);
        if (assignmentModel != null) {
            binding.updateTitleAssignmentEditText.setText(assignmentModel.getTitle());

            // String date = assignmentModel.getDueDate();
            if (assignmentModel.getDueDate().equals("N/A")) {
                binding.updateSelectedDueDate.setText("__-__-____");
            } else {
                binding.updateSelectedDueDate.setText(assignmentModel.getDueDate());
            }

            if (assignmentModel.getCourse().equals("")) { //*
                binding.updateSelectedCourse.setText("________________"); //*
            } else { //*
                binding.updateSelectedCourse.setText(assignmentModel.getCourse()); //*
            }

            //binding.updateCourseEditText.setText(assignmentModel.getCourse());
            binding.updateDescriptionEditText.setText(assignmentModel.getDescription());
        }

        binding.updateSaveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAssignmentUpdateDialog();
            }
        });
    }


    void confirmAssignmentUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + binding.updateTitleAssignmentEditText.getText().toString().trim() + " ?");
        builder.setMessage("Are you sure you want to update this assignment?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handleUpdateAssignmentAction(); // Proceed with updating the assignment if "Yes" is clicked
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


    private void handleUpdateAssignmentAction() {

        String date = (String) updateSelectedDueDate.getText();
        if (date.equals("__-__-____")) {
            updateSelectedDueDate.setText("N/A");
        }

        String course = (String) updateSelectedCourse.getText(); //*
        if (course.equals("________________")) { //*
            updateSelectedCourse.setText(""); //*
        }

        db.updateAssignment(assignmentId, //*
                binding.updateTitleAssignmentEditText.getText().toString().trim(),
                binding.updateSelectedDueDate.getText().toString().trim(),
                binding.updateSelectedCourse.getText().toString().trim(),
                binding.updateDescriptionEditText.getText().toString().trim());
//        db.updateAssignment(assignmentId,
//                binding.updateTitleAssignmentEditText.getText().toString().trim(),
//                binding.updateSelectedDueDate.getText().toString().trim(),
//                binding.updateCourseEditText.getText().toString().trim(),
//                binding.updateDescriptionEditText.getText().toString().trim());
        finish();
    }


}