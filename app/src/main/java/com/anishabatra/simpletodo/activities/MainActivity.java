package com.anishabatra.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.anishabatra.simpletodo.R;
import com.anishabatra.simpletodo.models.Todo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // numeric code to identify the edit activity
    public final static int EDIT_REQUEST_CODE = 20;
    // keys used for passing data between activities
    public final static String TASK_NAME = "updatedTaskName";
    public final static String ITEM_POSITION = "itemPosition";

    ArrayList<Todo> items;
    ArrayAdapter<Todo> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<Todo>(this, android.R.layout.simple_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        // mock data
        //items.add("First item");
        //items.add("Second item");

        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Todo todo = new Todo();
        todo.setTaskName(itemText);
        itemsAdapter.add(todo);

        etNewItem.setText("");
        writeItems();

        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Item removed from list: " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        // set up item listener for edit (regular click)
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create the new activity
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                // pass the data being edited
                i.putExtra(TASK_NAME, items.get(position));
                i.putExtra(ITEM_POSITION, position);
                // display the activity
                startActivityForResult(i, EDIT_REQUEST_CODE);

            }
        });
    }

    private String getDataFilePath() {
        return "todo.txt";
    }

    private void readItems() {
//        try {
//            File f = new File(getDataFilePath());
//            if (!f.exists()) {
//                f.createNewFile();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("MainActivity", "Error creating file", e);
//        }

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
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            // extract updated item text from intent extras

            String updatedTaskName = data.getStringExtra(TASK_NAME);
            // extract original position of edited item
            int position = data.getExtras().getInt(ITEM_POSITION);
            // update the model with the new item text at the edited position
            Todo todo = items.get(position);
            todo.setTaskName(updatedTaskName);
            // notify the adapter that the model changed
            itemsAdapter.notifyDataSetChanged();
            // persist the changed model
            writeItems();
            // notify the user the operation completed ok
            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
