package com.anishabatra.simpletodo.models;
import java.io.Serializable;
import java.util.Date;

public class Todo implements Serializable {

    private String taskName;
    private Date dueDate;
    private String priority;
    private String notes;

    private boolean IsComplete;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isComplete() {
        return IsComplete;
    }

    public void setComplete(boolean complete) {
        IsComplete = complete;
    }


    @Override
    public String toString() {
        return taskName;
    }

}
