package com.example.chronosapp.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.R;
import com.example.chronosapp.ui.home.ItemsDetailsForHomeFragmentSetListener;
import com.example.chronosapp.ui.itemList.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragment extends Fragment implements AddNewListDialogListener, EditListDialogListener,
                                            GetListsBackgroundTaskListener,
                                            RemoveListBackgroundTaskListener,
                                            GetItemsForHomeFragmentBackgroundTaskListener{

    private ListViewModel listViewModel;

    private RecyclerView mRecyclerView;
    private ArrayList<ListItem> mListItems;
    private ListItemAdapter mlistItemAdapter;

    private ViewGroup root;
    private String sharedUserId;

    private Context contextOfFragment;

    private Fragment fragment;

    public ListFragment(Fragment fragment)
    {
        this.fragment = fragment;
    }

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
        contextOfFragment = container.getContext();


        FloatingActionButton fab = root.findViewById(R.id.itemListFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddListDialog();
            }
        });

        mRecyclerView = root.findViewById(R.id.ToDoListsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mListItems = new ArrayList<>();
        mlistItemAdapter = new ListItemAdapter(root.getContext(),mListItems);
        mRecyclerView.setAdapter(mlistItemAdapter);

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("userDataSharedPref", Context.MODE_APPEND);
        sharedUserId = sharedPreferences.getString("userid","");

        getListsFromDatabase();
        return root;
    }

    public void getItemsForHomeFragment()
    {
        StringBuilder listIDs = new StringBuilder();
        for(int i=0;i<mListItems.size();i++)
        {
            listIDs.append(mListItems.get(i).getListID());
            if(i!=mListItems.size()-1)
                listIDs.append(",");
        }

        Log.d("ListFragment - getItemsForHomeFragment",listIDs.toString());

        GetItemsForHomeFragmentBackgroundTask getItemsForHomeFragmentBackgroundTask = new GetItemsForHomeFragmentBackgroundTask(this);
        getItemsForHomeFragmentBackgroundTask.execute(listIDs.toString());
    }

    public void getListsFromDatabase()
    {
//        setTargetFragment(ListFragment.this,1);
        GetListsBackgroundTask getListsBackgroundTask = new GetListsBackgroundTask(this);
        getListsBackgroundTask.execute(sharedUserId);
    }

    public void openAddListDialog()
    {
        AddNewListDialog addNewListDialog = new AddNewListDialog();
        addNewListDialog.setTargetFragment(ListFragment.this,1);
        addNewListDialog.show(getParentFragmentManager(), "Add new list");
    }

    public void openEditListDialog(int listId, String currentListName)
    {
        EditListDialog editListDialog = new EditListDialog(listId, currentListName);
        editListDialog.setTargetFragment(ListFragment.this,1);
        editListDialog.show(getParentFragmentManager(), "Edit list");
    }

    /**
     * Apply fetched lists from database to the view
     */
    private void applyLists() {
//        // Get the resources from the XML file.
//        String[] listItemTitles = getResources()
//                .getStringArray(R.array.listItemTitles);
//        String[] listItemDescriptions = getResources()
//                .getStringArray(R.array.listItemDescription);
//        TypedArray listItemsBackgrounds = getResources()
//                .obtainTypedArray(R.array.listItemBackgrounds);
//
//        // Clear the existing data (to avoid duplication).
//        mListItems.clear();

//        // Create the ArrayList of List Item objects with the titles and
//        // information about each list
//        for (int i = 0; i < listItemTitles.length; i++) {
//            mListItems.add(new ListItem(listItemTitles[i], listItemDescriptions[i],
//                    listItemsBackgrounds.getResourceId(i, 0)));
//        }
//
//        // Recycle the typed array.
//        listItemsBackgrounds.recycle();
        mlistItemAdapter.setListItemData(mListItems);
        Log.d("size of array of list item: ",String.valueOf(mListItems.size()));
        Log.d("size of list item adapter: ",String.valueOf(mlistItemAdapter.getItemCount()));


        // Notify the adapter of the change.
//        mlistItemAdapter.notifyDataSetChanged();

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
                // Remove from database.
                removeListFromDatabase(viewHolder, mListItems.get(viewHolder.getAdapterPosition()));
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
    }

    public void removeListFromDatabase(RecyclerView.ViewHolder viewHolder, ListItem listItem)
    {
        Snackbar.make(root, "id list: "+listItem.getListID()+", listname: "+listItem.getTitle(),
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Log.d("removeListFromDatabase", "id list: "+listItem.getListID()+", listname: "+listItem.getTitle());

        RemoveListBackgroundTask removeListBackgroundTask = new RemoveListBackgroundTask(this, viewHolder, listItem);
        removeListBackgroundTask.execute();
    }

    @Override
    public void addNewList(String listName) {
        Snackbar.make(root, "listName: "+listName, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        AddListBackgroundTask addListBackgroundTask = new AddListBackgroundTask(root.getContext());
        addListBackgroundTask.execute(sharedUserId, listName);

        getListsFromDatabase();
    }

    @Override
    public void getLists(ArrayList<ListItem> arrayOfLists) {
        mListItems.clear();
        mListItems.addAll(arrayOfLists);
        for(int i=0;i<mListItems.size();i++)
            Log.d("Content of mListItems: ",
                    "id= "+ mListItems.get(i).getListID()
                    +", listname= "+mListItems.get(i).getTitle());
        applyLists();
        getItemsForHomeFragment();
    }

    @Override
    public void removeListFromUI(RecyclerView.ViewHolder viewHolder) {
        // Remove the item from the dataset.
        mListItems.remove(viewHolder.getAdapterPosition());

        // Notify the adapter.
        mlistItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public void restoreListsFromDb() {
        getListsFromDatabase();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case 1:
                String currentListName = mlistItemAdapter.getTitleByListId(item.getGroupId());
                if(currentListName == null)
                    currentListName = "none";
                //Toast.makeText(contextOfFragment,"Edit list clicked: "+currentListName,Toast.LENGTH_LONG).show();
                openEditListDialog(item.getGroupId(), currentListName);
                break;
            default:
                //Toast.makeText(contextOfFragment,"No such case",Toast.LENGTH_LONG).show();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void editList(int listId, String newListName) {
        Snackbar.make(root, "newListName: "+newListName, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        EditListBackgroundTask editListBackgroundTask = new EditListBackgroundTask(root.getContext());
        editListBackgroundTask.execute(String.valueOf(listId), newListName);

        getListsFromDatabase();
    }

    @Override
    public void sendItemsToHomeFragment(ArrayList<Item> mItemArrayList) {

        ItemsDetailsForHomeFragmentSetListener listener = (ItemsDetailsForHomeFragmentSetListener) fragment;
        listener.getItemsForHomeFragment(mItemArrayList);
    }
}