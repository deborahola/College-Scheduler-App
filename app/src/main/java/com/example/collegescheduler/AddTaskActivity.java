package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import com.example.collegescheduler.databinding.ActivityAddTaskBinding;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    private ActivityAddTaskBinding binding;

    //EditText titleTaskEditText, courseTaskEditText, moreInfoEditText;
    EditText titleTaskEditText, moreInfoEditText; //*
    TextView selectedTaskDueDate;
    CheckBox Urgent, High, Medium, Low;
    Button pickTaskDueDate;

    // int numDayOfWeek;
    String stringDayOfWeek = "";
    String selectedPriority = "";

    TextView selectedTaskCourse; //*
    Button pickTaskCourse; //*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pickTaskDueDate = findViewById(R.id.pickTaskDueDate);
        selectedTaskDueDate = findViewById(R.id.selectedTaskDueDate);

        pickTaskCourse = findViewById(R.id.pickTaskCourse); //*
        selectedTaskCourse = findViewById(R.id.selectedTaskCourse); //*

        binding.pickTaskDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                // numDayOfWeek = c.get(Calendar.DAY_OF_WEEK);

//                switch (numDayOfWeek) {
//                    case Calendar.SUNDAY:
//                        stringDayOfWeek = "Sun";
//                        break;
//                    case Calendar.MONDAY:
//                        stringDayOfWeek = "Mon";
//                        break;
//                    case Calendar.TUESDAY:
//                        stringDayOfWeek = "Tue";
//                        break;
//                    case Calendar.WEDNESDAY:
//                        stringDayOfWeek = "Wed";
//                        break;
//                    case Calendar.THURSDAY:
//                        stringDayOfWeek = "Thu";
//                        break;
//                    case Calendar.FRIDAY:
//                        stringDayOfWeek = "Fri";
//                        break;
//                    case Calendar.SATURDAY:
//                        stringDayOfWeek = "Sat";
//                        break;
//                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.selectedTaskDueDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.pickTaskCourse.setOnClickListener(new View.OnClickListener() { //*
            @Override
            public void onClick(View view) {
                ClassesDatabaseHelper classDB = new ClassesDatabaseHelper(AddTaskActivity.this);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTaskActivity.this);
                builder.setTitle("Pick Course");
                String[] finalClasses = classes;
                builder.setItems(finalClasses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < finalClasses.length; i++) {
                            if (i == which) {
                                if (finalClasses[i].equals("You have no classes. Add a class first.")
                                        || finalClasses[i].equals("No Class")) {
                                    binding.selectedTaskCourse.setText("________________");
                                    break;
                                }
                                binding.selectedTaskCourse.setText(finalClasses[i]);
                                break;
                            }
                        }
                    }
                });
                builder.show();
            }
        });


        titleTaskEditText = findViewById(R.id.titleTaskEditText);
        selectedTaskDueDate = findViewById(R.id.selectedTaskDueDate);
        //courseTaskEditText = findViewById(R.id.courseTaskEditText);
        moreInfoEditText = findViewById(R.id.moreInfoEditText);

        Urgent = findViewById(R.id.Urgent);
        High = findViewById(R.id.High);
        Medium = findViewById(R.id.Medium);
        Low = findViewById(R.id.Low);

//        String theDate = (String) binding.selectedTaskDueDate.getText();
//        if (!(theDate.equals("__-__-____"))) {
//            switch (numDayOfWeek) {
//                case Calendar.SUNDAY:
//                    stringDayOfWeek = "Sun";
//                    break;
//                case Calendar.MONDAY:
//                    stringDayOfWeek = "Mon";
//                    break;
//                case Calendar.TUESDAY:
//                    stringDayOfWeek = "Tue";
//                    break;
//                case Calendar.WEDNESDAY:
//                    stringDayOfWeek = "Wed";
//                    break;
//                case Calendar.THURSDAY:
//                    stringDayOfWeek = "Thu";
//                    break;
//                case Calendar.FRIDAY:
//                    stringDayOfWeek = "Fri";
//                    break;
//                case Calendar.SATURDAY:
//                    stringDayOfWeek = "Sat";
//                    break;
//            }
//        } else {
//            stringDayOfWeek = "";
//        }

//        if (binding.Urgent.isChecked()) {
//            selectedPriority = "Urgent";
//        } else if (binding.High.isChecked()) {
//            selectedPriority = "High";
//        } else if (binding.Medium.isChecked()) {
//            selectedPriority = "Medium";
//        } else if (binding.Low.isChecked()) {
//            selectedPriority = "Low";
//        } else {
//            selectedPriority = "None";
//        }

        binding.saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                String date = (String) selectedTaskDueDate.getText();
                if (date.equals("__-__-____")) {
                    selectedTaskDueDate.setText("N/A");
                }

                String course = (String) selectedTaskCourse.getText(); //*
                if (course.equals("________________")) { //*
                    selectedTaskCourse.setText(""); //*
                }

//                String theDate = (String) binding.selectedTaskDueDate.getText();
//                if (!(theDate.equals("__-__-____"))) {
//                    switch (numDayOfWeek) {
//                        case Calendar.SUNDAY:
//                            stringDayOfWeek = "Sun";
//                            break;
//                        case Calendar.MONDAY:
//                            stringDayOfWeek = "Mon";
//                            break;
//                        case Calendar.TUESDAY:
//                            stringDayOfWeek = "Tue";
//                            break;
//                        case Calendar.WEDNESDAY:
//                            stringDayOfWeek = "Wed";
//                            break;
//                        case Calendar.THURSDAY:
//                            // stringDayOfWeek = "Thu";
//                            break;
//                        case Calendar.FRIDAY:
//                            stringDayOfWeek = "Fri";
//                            break;
//                        case Calendar.SATURDAY:
//                            stringDayOfWeek = "Sat";
//                            break;
//                    }
//                } else {
//                    stringDayOfWeek = "";
//                }

                TasksDatabaseHelper db = new TasksDatabaseHelper(AddTaskActivity.this);
//                db.addTask(binding.titleTaskEditText.getText().toString().trim(),
//                        binding.selectedTaskDueDate.getText().toString().trim(),
//                        stringDayOfWeek,
//                        binding.courseTaskEditText.getText().toString().trim(),
//                        binding.moreInfoEditText.getText().toString().trim(),
//                        selectedPriority,
//                        "INCOMPLETE");

                db.addTask(binding.titleTaskEditText.getText().toString().trim(),
                        binding.selectedTaskDueDate.getText().toString().trim(),
                        binding.selectedTaskCourse.getText().toString().trim(),
                        binding.moreInfoEditText.getText().toString().trim(),
                        selectedPriority,
                        "INCOMPLETE");
//                db.addTask(binding.titleTaskEditText.getText().toString().trim(),
//                        binding.selectedTaskDueDate.getText().toString().trim(),
//                        binding.courseTaskEditText.getText().toString().trim(),
//                        binding.moreInfoEditText.getText().toString().trim(),
//                        selectedPriority,
//                        "INCOMPLETE");
                finish();
            }
        });
    }

}