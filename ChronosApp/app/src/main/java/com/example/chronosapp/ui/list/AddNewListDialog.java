package com.example.chronosapp.ui.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.chronosapp.R;

public class AddNewListDialog extends AppCompatDialogFragment {
    private EditText newListNameEditText;
    private AddNewListDialogListener addNewListDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_list_dialog, null);

        view.setDrawingCacheBackgroundColor(R.drawable.light_bar);
        builder.setView(view)
                .setTitle("Add new list")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newListName = newListNameEditText.getText().toString().trim();

                        if (newListName.isEmpty()){
                            return;
                        }
                        addNewListDialogListener.addNewList(newListName);
                    }
                });
        newListNameEditText = view.findViewById(R.id.add_new_list_dialog_edittext);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addNewListDialogListener = (AddNewListDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddNewListDialogListener");
        }
    }
}
