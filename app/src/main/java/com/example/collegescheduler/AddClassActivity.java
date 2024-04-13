package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import com.example.collegescheduler.databinding.ActivityAddClassBinding;

import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class AddClassActivity extends AppCompatActivity {

    private ActivityAddClassBinding binding;

    EditText titleEditText, coursenameEditText, startTimeEditText, endTimeEditText, locationEditText, instructorEditText;
    CheckBox Monday, Tuesday, Wednesday, Thursday, Friday;
    RadioButton AM, PM, am, pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        titleEditText = findViewById(R.id.titleEditText);

        coursenameEditText = findViewById(R.id.coursenameEditText);

        Monday = findViewById(R.id.Monday);
        Tuesday = findViewById(R.id.Tuesday);
        Wednesday = findViewById(R.id.Wednesday);
        Thursday = findViewById(R.id.Thursday);
        Friday = findViewById(R.id.Friday);

        startTimeEditText = findViewById(R.id.startTimeEditText);
        AM = findViewById(R.id.AM);
        PM = findViewById(R.id.PM);

        endTimeEditText = findViewById(R.id.endTimeEditText);
        am = findViewById(R.id.am);
        pm = findViewById(R.id.pm);

        locationEditText = findViewById(R.id.locationEditText);

        instructorEditText = findViewById(R.id.instructorEditText);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder selectedDays = new StringBuilder();
                if (binding.Monday.isChecked()) {
                    selectedDays.append("Mon, ");
                }
                if (binding.Tuesday.isChecked()) {
                    selectedDays.append("Tue, ");
                }
                if (binding.Wednesday.isChecked()) {
                    selectedDays.append("Wed, ");
                }
                if (binding.Thursday.isChecked()) {
                    selectedDays.append("Thu, ");
                }
                if (binding.Friday.isChecked()) {
                    selectedDays.append("Fri, ");
                }
                String days = selectedDays.toString().trim();
                if (days.endsWith(", ")) {
                    days = days.substring(0, days.length() - 2);
                }
                if (days.endsWith(",")) {
                    days = days.substring(0, days.length() - 1);
                }

                String startTime = binding.startTimeEditText.getText().toString().trim();
                String startSuffix = binding.AM.isChecked() ? " AM" : " PM";
                String endTime = binding.endTimeEditText.getText().toString().trim();
                String endSuffix = binding.am.isChecked() ? " AM" : " PM";

                String timeRange;

                if (startTime.equals("") && endTime.equals("")) {
                    timeRange = "";
                } else {
                    timeRange = startTime + startSuffix + " - " + endTime + endSuffix;
                }

                ClassesDatabaseHelper db = new ClassesDatabaseHelper(AddClassActivity.this);
                db.addClass(binding.titleEditText.getText().toString().trim(),
                        binding.coursenameEditText.getText().toString().trim(),
                        days,
                        timeRange,
                        binding.locationEditText.getText().toString().trim(),
                        binding.instructorEditText.getText().toString().trim());

                finish();
            }
        });
    }

}