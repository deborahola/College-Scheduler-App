package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import com.example.collegescheduler.databinding.ActivityAddAssignmentBinding;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.List;

public class AddAssignmentActivity extends AppCompatActivity {

    private ActivityAddAssignmentBinding binding;

    // EditText titleAssignmentEditText, courseEditText, descriptionEditText;
    EditText titleAssignmentEditText, descriptionEditText; //*
    TextView selectedDueDate;
    Button pickDueDate;

    // int numDayOfWeek;

    TextView selectedCourse; //*
    Button pickCourse; //*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pickDueDate = findViewById(R.id.pickDueDate);
        selectedDueDate = findViewById(R.id.selectedDueDate);

        pickCourse = findViewById(R.id.pickCourse); //*
        selectedCourse = findViewById(R.id.selectedCourse); //*

        binding.pickDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                // numDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddAssignmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.selectedDueDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.pickCourse.setOnClickListener(new View.OnClickListener() { //*
            @Override
            public void onClick(View view) {
                ClassesDatabaseHelper classDB = new ClassesDatabaseHelper(AddAssignmentActivity.this);
                List<ClassModel> classList = classDB.getAllClasses();
                String[] classes = new String[classList.size()];
                if (classes.length == 0) {
                    classes = new String[]{"You have no classes. Add a class first."};
                } else {
                    for (int i = 0; i < classList.size(); i++) {
                        classes[i] = classList.get(i).getTitle();
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddAssignmentActivity.this);
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
                                binding.selectedCourse.setText(finalClasses[i]);
                                break;
                            }
                        }
                    }
                });
                builder.show();
            }
        });



        titleAssignmentEditText = findViewById(R.id.titleAssignmentEditText);
        //courseEditText = findViewById(R.id.courseEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        // convert numDayOfWeek to String (Mon, Tue, Wed, Thu, Fri)

        // String stringDayOfWeek = _ (with if statements / cases / switch)


        binding.saveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = (String) selectedDueDate.getText();
                if (date.equals("__-__-____")) {
                    selectedDueDate.setText("N/A");
                }

                String course = (String) selectedCourse.getText(); //*
                if (course.equals("________________")) { //*
                    selectedCourse.setText(""); //*
                }


                AssignmentsDatabaseHelper db = new AssignmentsDatabaseHelper(AddAssignmentActivity.this);
                db.addAssignment(binding.titleAssignmentEditText.getText().toString().trim(), //*
                        binding.selectedDueDate.getText().toString().trim(),
                        binding.selectedCourse.getText().toString().trim(),
                        binding.descriptionEditText.getText().toString().trim());
//                db.addAssignment(binding.titleAssignmentEditText.getText().toString().trim(),
//                        binding.selectedDueDate.getText().toString().trim(),
//                        binding.courseEditText.getText().toString().trim(),
//                        binding.descriptionEditText.getText().toString().trim());


                // put stringDayOfWeek into database too

                finish();
            }
        });
    }


}