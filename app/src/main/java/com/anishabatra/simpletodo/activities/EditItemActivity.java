package com.anishabatra.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.anishabatra.simpletodo.R;

public class EditItemActivity extends AppCompatActivity {


    EditText eItemText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        eItemText = findViewById(R.id.etItemText);
        eItemText.setText(getIntent().getStringExtra(MainActivity.TASK_NAME));
        position = getIntent().getIntExtra(MainActivity.ITEM_POSITION, 0);

        getSupportActionBar().setTitle("Edit Text");
    }

    public void onSaveItem(View view) {
        Intent i = new Intent();
        i.putExtra(MainActivity.TASK_NAME, eItemText.getText().toString());
        i.putExtra(MainActivity.ITEM_POSITION, position);
        // set the intent as the result of the activity
        setResult(RESULT_OK, i);
        // close the activity and redirect to main
        finish();

    }
}
