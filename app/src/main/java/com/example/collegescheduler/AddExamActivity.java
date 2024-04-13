package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import com.example.collegescheduler.databinding.ActivityAddExamBinding;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class AddExamActivity extends AppCompatActivity {

    private ActivityAddExamBinding binding;

    //EditText titleExamEditText, examCourseEditText, startTimeExamEditText, endTimeExamEditText, locationExamEditText;
    EditText titleExamEditText, startTimeExamEditText, endTimeExamEditText, locationExamEditText; //*
    RadioButton AMexam, PMexam, amexam, pmexam;

    TextView selectedExamDate;
    Button pickExamDate;

    TextView selectedExamCourse; //*
    Button pickExamCourse; //*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pickExamDate = findViewById(R.id.pickExamDate);
        selectedExamDate = findViewById(R.id.selectedExamDate);

        pickExamCourse = findViewById(R.id.pickExamCourse); //*
        selectedExamCourse = findViewById(R.id.selectedExamCourse); //*

        binding.pickExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddExamActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.selectedExamDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.pickExamCourse.setOnClickListener(new View.OnClickListener() { //*
            @Override
            public void onClick(View view) {
                ClassesDatabaseHelper classDB = new ClassesDatabaseHelper(AddExamActivity.this);
                List<ClassModel> classList = classDB.getAllClasses();
                String[] classes = new String[classList.size()];
                if (classes.length == 0) {
                    classes = new String[]{"You have no classes. Add a class first."};
                } else {
                    for (int i = 0; i < classList.size(); i++) {
                        classes[i] = classList.get(i).getTitle();
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddExamActivity.this);
                builder.setTitle("Pick Course");
                String[] finalClasses = classes;
                builder.setItems(finalClasses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < finalClasses.length; i++) {
                            if (i == which) {
                                if (finalClasses[i].equals("You have no classes. Add a class first.")) {
                                    break;
                                }
                                binding.selectedExamCourse.setText(finalClasses[i]);
                                break;
                            }
                        }
                    }
                });
                builder.show();
            }
        });

        titleExamEditText = findViewById(R.id.titleExamEditText);
        //examCourseEditText = findViewById(R.id.examCourseEditText);

        startTimeExamEditText = findViewById(R.id.startTimeExamEditText);
        AMexam = findViewById(R.id.AMexam);
        PMexam = findViewById(R.id.PMexam);

        endTimeExamEditText = findViewById(R.id.endTimeExamEditText);
        amexam = findViewById(R.id.amexam);
        pmexam = findViewById(R.id.pmexam);

        locationExamEditText = findViewById(R.id.locationExamEditText);

        binding.saveExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = (String) selectedExamDate.getText();
                if (date.equals("__-__-____")) {
                    selectedExamDate.setText("");
                }

                String course = (String) selectedExamCourse.getText(); //*
                if (course.equals("________________")) { //*
                    selectedExamCourse.setText(""); //*
                }

                String startTime = binding.startTimeExamEditText.getText().toString().trim();
                String startSuffix = binding.AMexam.isChecked() ? " AM" : " PM";
                String endTime = binding.endTimeExamEditText.getText().toString().trim();
                String endSuffix = binding.amexam.isChecked() ? " AM" : " PM";

                String timeRange;

                if (startTime.equals("") && endTime.equals("")) {
                    timeRange = "";
                } else {
                    timeRange = startTime + startSuffix + " - " + endTime + endSuffix;
                }

                ExamsDatabaseHelper db = new ExamsDatabaseHelper(AddExamActivity.this);
                db.addExam(binding.titleExamEditText.getText().toString().trim(), //*
                        binding.selectedExamDate.getText().toString().trim(),
                        binding.selectedExamCourse.getText().toString().trim(),
                        timeRange,
                        binding.locationExamEditText.getText().toString().trim());
//                db.addExam(binding.titleExamEditText.getText().toString().trim(),
//                        binding.selectedExamDate.getText().toString().trim(),
//                        binding.examCourseEditText.getText().toString().trim(),
//                        timeRange,
//                        binding.locationExamEditText.getText().toString().trim());

                finish();
            }
        });
    }

}