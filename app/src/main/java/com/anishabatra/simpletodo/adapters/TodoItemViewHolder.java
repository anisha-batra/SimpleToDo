package com.anishabatra.simpletodo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.anishabatra.simpletodo.R;
import com.anishabatra.simpletodo.models.Todo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TodoItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewTaskName;
    private TextView textViewDate;
    private TextView textViewMonth;
    private TextView textViewPriority;
    private Switch switchIsComplete;

    public TodoItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
        textViewMonth = itemView.findViewById(R.id.textViewMonth);
        textViewDate = itemView.findViewById(R.id.textViewDate);
        textViewPriority = itemView.findViewById(R.id.textViewTaskPriority);
        switchIsComplete = itemView.findViewById(R.id.switchIsComplete);
    }

    public void setTaskItem(Todo todo) {
        textViewTaskName.setText(todo.getTaskName());
        textViewPriority.setText(todo.getPriority());

        if(todo.getDueDate() != null) {
            DateFormat dfForMonth = new SimpleDateFormat("MMM");
            textViewMonth.setText(dfForMonth.format(todo.getDueDate()));

            DateFormat dfForDate = new SimpleDateFormat("dd");
            textViewDate.setText(dfForDate.format(todo.getDueDate()));
        } else {
            textViewMonth.setText("- - -");
            textViewDate.setText("- -");
        }

        if(todo.isComplete()) {
            switchIsComplete.setChecked(true);
        } else {
            switchIsComplete.setChecked(false);
        }
    }
}

