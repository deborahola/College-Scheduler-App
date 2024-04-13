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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private Context context;
    private List<TaskModel> taskList;

    private TasksDatabaseHelper db;


    TasksAdapter(Context context, List<TaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
        db = new TasksDatabaseHelper(context);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView titleTaskTextView, dueDateTaskTextView, courseTaskTextView, moreInfoTextView, priorityTextView, taskStatusTextView;
        // TextView taskDayTextView, taskDateNumTextView, taskMonthTextView;
        ImageView updateTaskButton;
        ImageView deleteTaskButton;
        CheckBox statusTaskButton;
        ImageView urgent_icon;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTaskTextView = itemView.findViewById(R.id.titleTaskTextView);
            dueDateTaskTextView = itemView.findViewById(R.id.dueDateTaskTextView);
            courseTaskTextView = itemView.findViewById(R.id.courseTaskTextView);
            moreInfoTextView = itemView.findViewById(R.id.moreInfoTextView);
            priorityTextView = itemView.findViewById(R.id.priorityTextView);
            taskStatusTextView = itemView.findViewById(R.id.taskStatusTextView);

//            taskDayTextView = itemView.findViewById(R.id.taskDayTextView);
//            taskDateNumTextView = itemView.findViewById(R.id.taskDateNumTextView);
//            taskMonthTextView = itemView.findViewById(R.id.taskMonthTextView);

            urgent_icon = itemView.findViewById(R.id.urgent_icon);

            updateTaskButton = itemView.findViewById(R.id.updateTaskButton);
            deleteTaskButton = itemView.findViewById(R.id.deleteTaskButton);
            statusTaskButton = itemView.findViewById(R.id.statusTaskButton);
        }

    }

    @NonNull
    @Override
    public TasksAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.simple_task_item, parent, false); // task_item
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.TaskViewHolder holder, int position) {
        TaskModel taskModel = taskList.get(position);
        if (taskModel != null) {
            holder.titleTaskTextView.setText(taskModel.getTitle());


            String date = (String) holder.dueDateTaskTextView.getText();
            if (date.equals("__-__-____")) {
                holder.dueDateTaskTextView.setText("N/A");
                //holder.taskDayTextView.setText("");
                //holder.taskDateNumTextView.setText("");
                //holder.taskMonthTextView.setText("");
            } else {
                holder.dueDateTaskTextView.setText(taskModel.getDueDate());
//                holder.taskDayTextView.setText(taskModel.getDayOfWeek());
//
//                String[] dateParts = date.split("-");
//                String taskMonth = dateParts[0];
//                String taskDateNum = dateParts[1];
//                holder.taskDateNumTextView.setText(taskDateNum);
//
//                switch (taskMonth) {
//                    case "1":
//                        taskMonth = "Jan";
//                        break;
//                    case "2":
//                        taskMonth = "Feb";
//                        break;
//                    case "3":
//                        taskMonth = "Mar";
//                        break;
//                    case "4":
//                        taskMonth = "Apr";
//                        break;
//                    case "5":
//                        taskMonth = "May";
//                        break;
//                    case "6":
//                        taskMonth = "Jun";
//                        break;
//                    case "7":
//                        taskMonth = "Jul";
//                        break;
//                    case "8":
//                        taskMonth = "Aug";
//                        break;
//                    case "9":
//                        taskMonth = "Sep";
//                        break;
//                    case "10":
//                        taskMonth = "Oct";
//                        break;
//                    case "11":
//                        taskMonth = "Nov";
//                        break;
//                    case "12":
//                        taskMonth = "Dec";
//                        break;
//                    default:
//                        break;
//                }
//
//                holder.taskMonthTextView.setText(taskMonth);
            }

            holder.courseTaskTextView.setText(taskModel.getCourse());
            holder.moreInfoTextView.setText(taskModel.getMoreInfo());
            holder.priorityTextView.setText(taskModel.getPriority());

            String userSelectedPriority = (String) holder.priorityTextView.getText();
            switch (userSelectedPriority) {
                case "Urgent":
                    holder.priorityTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                    holder.urgent_icon.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                    break;
                case "High":
                    holder.priorityTextView.setTextColor(ContextCompat.getColor(context, R.color.orange));
                    holder.urgent_icon.setColorFilter(ContextCompat.getColor(context, R.color.orange), android.graphics.PorterDuff.Mode.SRC_IN);
                    break;
                case "Medium":
                    holder.priorityTextView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
                    holder.urgent_icon.setColorFilter(ContextCompat.getColor(context, R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);

                    break;
                case "Low":
                    holder.priorityTextView.setTextColor(ContextCompat.getColor(context, R.color.light_green));
                    holder.urgent_icon.setColorFilter(ContextCompat.getColor(context, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);

                    break;
                case "None":
                    holder.priorityTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
                    holder.urgent_icon.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);

                    break;
            }

            holder.taskStatusTextView.setText(taskModel.getStatus());

            if (taskModel.getStatus().equals("COMPLETE")) {
                holder.statusTaskButton.setChecked(true);
            } else if (taskModel.getStatus().equals("INCOMPLETE")) {
                holder.statusTaskButton.setChecked(false);
            }

        }


        holder.updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateTaskActivity.class);
                intent.putExtra("id", taskModel.getId());
                context.startActivity(intent);
            }
        });


        holder.deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete " + holder.titleTaskTextView.getText().toString().trim() + " ?");
                builder.setMessage("Are you sure you want to delete this task?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteTask(taskModel.getId()); // Proceed with deleting the task if "Yes" is clicked
                        refreshTaskData(db.getAllTasks());
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


        holder.statusTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.statusTaskButton.isChecked()) {
                    holder.taskStatusTextView.setText("COMPLETE");
                    db.updateTask(taskModel.getId(), taskModel.getTitle(), taskModel.getDueDate(),
                            taskModel.getCourse(), taskModel.getMoreInfo(), taskModel.getPriority(), "COMPLETE");
                } else if (!(holder.statusTaskButton.isChecked())) {
                    holder.taskStatusTextView.setText("INCOMPLETE");
                    db.updateTask(taskModel.getId(), taskModel.getTitle(), taskModel.getDueDate(),
                            taskModel.getCourse(), taskModel.getMoreInfo(), taskModel.getPriority(), "INCOMPLETE");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    void refreshTaskData(List<TaskModel> updatedList) {
        taskList.clear();
        taskList.addAll(updatedList);
        notifyDataSetChanged();
    }

    public void sortByDueDate() {
        Collections.sort(taskList, new Comparator<TaskModel>() {
            @Override
            public int compare(TaskModel task1, TaskModel task2) {
                // return task1.getDueDate().compareTo(task2.getDueDate());
                DateFormat dateFormat = new SimpleDateFormat("M-dd-yyyy");
                DateFormat longDateFormat = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    Date dueDate1, dueDate2;
                    if (task1.getDueDate().indexOf('-') == 1) {
                        dueDate1 = dateFormat.parse(task1.getDueDate());
                    } else {
                        dueDate1 = longDateFormat.parse(task1.getDueDate());
                    }
                    if (task2.getDueDate().indexOf('-') == 1) {
                        dueDate2 = dateFormat.parse(task2.getDueDate());
                    } else {
                        dueDate2 = longDateFormat.parse(task2.getDueDate());
                    }
                    return dueDate1.compareTo(dueDate2);
                } catch (ParseException e) {
                    return task1.getDueDate().compareTo(task2.getDueDate());
                }
            }
        });
        notifyDataSetChanged();
    }

    public void sortByCourse() {
        Collections.sort(taskList, new Comparator<TaskModel>() {
            @Override
            public int compare(TaskModel task1, TaskModel task2) {
                return task1.getCourse().compareTo(task2.getCourse());
            }
        });
        notifyDataSetChanged();
    }

    /**
     * Completed tasks move to the bottom while incomplete tasks move to the top.
     * This method is currently not being used.
     */
    public void sortByStatus() {
        Collections.sort(taskList, new Comparator<TaskModel>() {
            @Override
            public int compare(TaskModel task1, TaskModel task2) {
                String status1 = task1.getStatus();
                String status2 = task2.getStatus();
                if (status1.equals("INCOMPLETE") && status2.equals("COMPLETE")) {
                    return -1;
                } else if (status1.equals("COMPLETE") && status2.equals("INCOMPLETE")) {
                    return 1;
                } else {
                    return status1.compareTo(status2);
                }
            }
        });
        notifyDataSetChanged();
    }


    public void showOnlyCompleteTasks() {
        List<TaskModel> completeTasks = new ArrayList<>();
        for (TaskModel task : db.getAllTasks()) {
            if (task.getStatus().equals("COMPLETE")) {
                completeTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(completeTasks);
        notifyDataSetChanged();
    }

    public void showOnlyIncompleteTasks() {
        List<TaskModel> incompleteTasks = new ArrayList<>();
        for (TaskModel task : db.getAllTasks()) {
            if (task.getStatus().equals("INCOMPLETE")) {
                incompleteTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(incompleteTasks);
        notifyDataSetChanged();
    }


    public void showAllTasks() {
        taskList.clear();
        taskList.addAll(db.getAllTasks());
        notifyDataSetChanged();
    }


    public void showOnlyUrgentTasks() {
        List<TaskModel> urgentTasks = new ArrayList<>();
        for (TaskModel task : db.getAllTasks()) {
            if (task.getPriority().equals("Urgent")) {
                urgentTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(urgentTasks);
        notifyDataSetChanged();
    }

    public void showOnlyHighTasks() {
        List<TaskModel> highTasks = new ArrayList<>();
        for (TaskModel task : db.getAllTasks()) {
            if (task.getPriority().equals("High")) {
                highTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(highTasks);
        notifyDataSetChanged();
    }

    public void showOnlyMediumTasks() {
        List<TaskModel> mediumTasks = new ArrayList<>();
        for (TaskModel task : db.getAllTasks()) {
            if (task.getPriority().equals("Medium")) {
                mediumTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(mediumTasks);
        notifyDataSetChanged();
    }

    public void showOnlyLowTasks() {
        List<TaskModel> lowTasks = new ArrayList<>();
        for (TaskModel task : db.getAllTasks()) {
            if (task.getPriority().equals("Low")) {
                lowTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(lowTasks);
        notifyDataSetChanged();
    }

    public void showOnlyNoneTasks() {
        List<TaskModel> noneTasks = new ArrayList<>();
        for (TaskModel task : db.getAllTasks()) {
            if (task.getPriority().equals("None")) {
                noneTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(noneTasks);
        notifyDataSetChanged();
    }






}