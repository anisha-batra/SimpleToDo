package com.anishabatra.simpletodo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anishabatra.simpletodo.R;
import com.anishabatra.simpletodo.models.Todo;

import java.util.ArrayList;

public class TodoItemsAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {

    private ArrayList<Todo> items;

    public TodoItemsAdapter(ArrayList<Todo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_item_list, viewGroup, false);
        TodoItemViewHolder todoItemViewHolder = new TodoItemViewHolder(v);

        return todoItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder todoItemViewHolder, int i) {
        todoItemViewHolder.setTaskItem(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
