package com.example.chronosapp.ui.list;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragment extends Fragment implements AddNewListDialog.AddNewListDialogListener {

    private ListViewModel listViewModel;

    private RecyclerView mRecyclerView;
    private ArrayList<ListItem> mListItems;
    private ListItemAdapter mlistItemAdapter;

    private ViewGroup root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        root = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
//        final TextView textView = root.findViewById(R.id.text_list);
//        listViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        mRecyclerView = root.findViewById(R.id.ToDoListsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mListItems = new ArrayList<>();
        mlistItemAdapter = new ListItemAdapter(root.getContext(),mListItems);
        mRecyclerView.setAdapter(mlistItemAdapter);

        //initData(); //getListsFromDatabase
        //applyData

        return root;
    }

    public void openDialog()
    {
        AddNewListDialog addNewListDialog = new AddNewListDialog();
        addNewListDialog.setTargetFragment(ListFragment.this,1);
        addNewListDialog.show(getParentFragmentManager(), "Add new list");
    }

    /**
     * Initialize the list items from resources.
     */
    private void initData() {
        // Get the resources from the XML file.
        String[] listItemTitles = getResources()
                .getStringArray(R.array.listItemTitles);
        String[] listItemDescriptions = getResources()
                .getStringArray(R.array.listItemDescription);
        TypedArray listItemsBackgrounds = getResources()
                .obtainTypedArray(R.array.listItemBackgrounds);

        // Clear the existing data (to avoid duplication).
        mListItems.clear();

        // Create the ArrayList of List Item objects with the titles and
        // information about each list
        for (int i = 0; i < listItemTitles.length; i++) {
            mListItems.add(new ListItem(listItemTitles[i], listItemDescriptions[i],
                    listItemsBackgrounds.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        listItemsBackgrounds.recycle();

        // Notify the adapter of the change.
        mlistItemAdapter.notifyDataSetChanged();

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The ListViewViewHolder that is being moved
             * @param target The ListViewViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mListItems, from, to);
                mlistItemAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                mListItems.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mlistItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void addNewList(String listName) {
        Snackbar.make(root, "listName: "+listName, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        //addListBackgroundTask
    }
}