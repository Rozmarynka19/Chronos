package com.example.chronosapp.ui.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.chronosapp.R;

public class EditListDialog extends AppCompatDialogFragment {
    private EditText newListNameEditText;
    private EditListDialogListener editListDialogListener;
    private int listId;
    private String currentListName;

    public EditListDialog(int listId, String currentListName)
    {
        this.listId = listId;
        this.currentListName = currentListName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_list_dialog, null);

        view.setDrawingCacheBackgroundColor(R.drawable.light_bar);
        builder.setView(view)
                .setTitle("Edit list: "+currentListName)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newListName = newListNameEditText.getText().toString();
                        editListDialogListener.editList(listId, newListName);
                    }
                });
        newListNameEditText = view.findViewById(R.id.edit_list_dialog_edittext);
        newListNameEditText.setText(currentListName);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            editListDialogListener = (EditListDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EditListDialogListener");
        }
    }
}
