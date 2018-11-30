package com.anishabatra.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.anishabatra.simpletodo.R;
import com.anishabatra.simpletodo.adapters.TodoItemsAdapter;
import com.anishabatra.simpletodo.fragments.EditTaskDialogFragment;
import com.anishabatra.simpletodo.models.Todo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // numeric code to identify the edit activity
    public final static int EDIT_REQUEST_CODE = 20;
    // keys used for passing data between activities
    public final static String TASK_NAME = "updatedTaskName";
    public final static String ITEM_POSITION = "itemPosition";

    private FloatingActionButton fab;

    private RecyclerView recyclerViewTodos;

    ArrayList<Todo> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTodos = findViewById(R.id.recyclerViewTodos);
        fab = findViewById(R.id.fabAddTask);

        readItems();

        items = new ArrayList<>();
        Todo todo1 = new Todo();
        todo1.setTaskName("Complete code path prework");
        todo1.setNotes("extra notes");
        todo1.setPriority("High");
        todo1.setComplete(false);
        try {
            todo1.setDueDate(new SimpleDateFormat("MM/dd/yyyy").parse("11/28/2018"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        items.add(todo1);

        Todo todo2 = new Todo();
        todo2.setTaskName("Complete code path prework part2");
        todo2.setNotes("extra notes part 2");
        todo2.setPriority("Medium");
        todo2.setComplete(true);
        try {
            todo2.setDueDate(new SimpleDateFormat("MM/dd/yyyy").parse("11/29/2018"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        items.add(todo2);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTodos.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        TodoItemsAdapter todoItemsAdapter = new TodoItemsAdapter(items);
        recyclerViewTodos.setAdapter(todoItemsAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewTaskDialog();
                // create the new activity
                //Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                // display the activity
                //startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });

        //setupListViewListener();
    }

    private void showAddNewTaskDialog() {
        String title = getResources().getString(R.string.title_add_task);

        FragmentManager fm = getSupportFragmentManager();
        EditTaskDialogFragment editNameDialogFragment = EditTaskDialogFragment.newInstance(title, new Todo());
        editNameDialogFragment.show(fm, "fragment_edit_task");
    }


//    public void onAddItem(View v) {
//        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
//        String itemText = etNewItem.getText().toString();
//        Todo todo = new Todo();
//        todo.setTaskName(itemText);
//        items.add(todo);
//
//        etNewItem.setText("");
//        writeItems();
//
//        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
//    }

    //  private void setupListViewListener() {
//        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("MainActivity", "Item removed from list: " + position);
//                items.remove(position);
//                itemsAdapter.notifyDataSetChanged();
//                writeItems();
//                return true;
//            }
//        });
//
//        // set up item listener for edit (regular click)
//        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // create the new activity
//                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//                // pass the data being edited
//                i.putExtra(TASK_NAME, items.get(position));
//                i.putExtra(ITEM_POSITION, position);
//                // display the activity
//                startActivityForResult(i, EDIT_REQUEST_CODE);
//
//            }
//        });
    //}

    private String getDataFilePath() {
        return "todo.txt";
    }

    private void readItems() {


        FileInputStream fis = null;
        try {
            fis = openFileInput(getDataFilePath());
            ObjectInputStream ois = new ObjectInputStream(fis);
            items = (ArrayList<Todo>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("MainActivity", "File not found", e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error reading file", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Class not found", e);
        }

        if (items == null) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getDataFilePath(), MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("MainActivity", "File not found", e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error writing file", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the edit activity completed ok
//        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
//            // extract updated item text from intent extras
//
//            String updatedTaskName = data.getStringExtra(TASK_NAME);
//            // extract original position of edited item
//            int position = data.getExtras().getInt(ITEM_POSITION);
//            // update the model with the new item text at the edited position
//            Todo todo = items.get(position);
//            todo.setTaskName(updatedTaskName);
//            // notify the adapter that the model changed
//            itemsAdapter.notifyDataSetChanged();
//            // persist the changed model
//            writeItems();
//            // notify the user the operation completed ok
//            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
    }
}
//}
