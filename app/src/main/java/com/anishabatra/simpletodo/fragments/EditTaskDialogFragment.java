package com.anishabatra.simpletodo.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.anishabatra.simpletodo.R;
import com.anishabatra.simpletodo.models.Todo;

public class EditTaskDialogFragment extends DialogFragment {
    private EditText editTextTaskName;

    public EditTaskDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditTaskDialogFragment newInstance(String title, Todo todo) {
        EditTaskDialogFragment frag = new EditTaskDialogFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("newTodo", todo);
        frag.setArguments(args);

        return frag;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);
//        //getDialog().setContentView(view);
//        return view;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        editTextTaskName = (EditText) view.findViewById(R.id.editTextTaskName);
        // Fetch arguments from bundle and set title

        Todo newTodo = (Todo) getArguments().getSerializable("newTodo");
        // Show soft keyboard automatically and request focus to field
//        editTextTaskName.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
        final FrameLayout frameView = new FrameLayout(getActivity());
        builder.setView(frameView);

        AlertDialog dialog = builder.create();

        String title = getArguments().getString("title", "Add New Task");
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        
        dialog.getLayoutInflater().inflate(R.layout.fragment_edit_task, frameView);

        return dialog;
    }

}