package com.example.collegescheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.collegescheduler.databinding.ActivityUpdateClassBinding;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class UpdateClassActivity extends AppCompatActivity {

    private ActivityUpdateClassBinding binding;
    private ClassesDatabaseHelper db;
    private int classId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new ClassesDatabaseHelper(this);

        classId = getIntent().getIntExtra("id", -1);
        if (classId == -1) {
            finish();
        }

        ClassModel classModel = db.getClassByID(classId);
        if (classModel != null) {
            binding.updateTitleEditText.setText(classModel.getTitle());
            binding.updateCoursenameEditText.setText(classModel.getCourseName());
            setDaysCheckboxes(classModel.getDays());
            setTimeRange(classModel.getTime());
            binding.updateLocationEditText.setText(classModel.getLocation());
            binding.updateInstructorEditText.setText(classModel.getInstructor());
        }

        binding.updateSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmClassUpdateDialog();
            }
        });
    }

    private void handleUpdateAction() {
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

        String startTime = binding.updateStartTimeEditText.getText().toString().trim();
        String startSuffix = binding.updateAM.isChecked() ? " AM" : " PM";
        String endTime = binding.updateEndTimeEditText.getText().toString().trim();
        String endSuffix = binding.updateam.isChecked() ? " AM" : " PM";

        String timeRange;

        if (startTime.equals("") && endTime.equals("")) {
            timeRange = "";
        } else {
            timeRange = startTime + startSuffix + " - " + endTime + endSuffix;
        }

        db.updateClass(classId,
                binding.updateTitleEditText.getText().toString().trim(),
                binding.updateCoursenameEditText.getText().toString().trim(),
                days,
                timeRange,
                binding.updateLocationEditText.getText().toString().trim(),
                binding.updateInstructorEditText.getText().toString().trim());

        finish();
    }

    private void setDaysCheckboxes(String days) {
        if (days.length() == 3) { // e.g., "Mon"
            if (days.equals("Mon")) {
                binding.Monday.setChecked(true);
            } else if (days.equals("Tue")) {
                binding.Tuesday.setChecked(true);
            } else if (days.equals("Wed")) {
                binding.Wednesday.setChecked(true);
            } else if (days.equals("Thu")) {
                binding.Thursday.setChecked(true);
            } else if (days.equals("Fri")) {
                binding.Friday.setChecked(true);
            }
        } else if (days.length() == 0) { // e.g., ""
            return;
        } else { // e.g., "Mon, Wed, Fri"
            String[] daysArray = days.split(", ");
            for (String day : daysArray) {
                switch (day) {
                    case "Mon":
                        binding.Monday.setChecked(true);
                        break;
                    case "Tue":
                        binding.Tuesday.setChecked(true);
                        break;
                    case "Wed":
                        binding.Wednesday.setChecked(true);
                        break;
                    case "Thu":
                        binding.Thursday.setChecked(true);
                        break;
                    case "Fri":
                        binding.Friday.setChecked(true);
                        break;
                }
            }
        }
    }

    private void setTimeRange(String timeRange) {

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

            binding.updateStartTimeEditText.setText(startTimeNum);
            if (startTime.endsWith("PM")) {
                binding.updatePM.setChecked(true);
            } else {
                binding.updateAM.setChecked(true);
            }

            binding.updateEndTimeEditText.setText(endTimeNum);
            if (endTime.endsWith("PM")) {
                binding.updatepm.setChecked(true);
            } else {
                binding.updateam.setChecked(true);
            }
        }

    }

    void confirmClassUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + binding.updateTitleEditText.getText().toString().trim() + " ?");
        builder.setMessage("Are you sure you want to update this class?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handleUpdateAction(); // Proceed with updating the class if "Yes" is clicked
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