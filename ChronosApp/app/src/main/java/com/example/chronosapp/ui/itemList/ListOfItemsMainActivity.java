package com.example.chronosapp.ui.itemList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class ListOfItemsMainActivity extends AppCompatActivity
                                    implements GetItemsBackgroundTaskListener,
                                                RemoveItemBackgroundTaskListener{
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mItemArrayList;
    private ItemAdapter itemAdapter;
    private String sharedUserId, listID;

    private Animation rotateOpen, rotateClose, fromBottom, toBottom;

    private FloatingActionButton addNewItemFab;
    private Button addNewTaskButton, addNewBillButton;

    private boolean isAddNewItemButtonClicked = false;

    private final static int NEW_TASK = 1, NEW_BILL = 2;

    private View itemListRelativeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_tasks);
        itemListRelativeView = (View) findViewById(R.id.itemListRelativeLayout);

        Intent details = getIntent();
        listID =  details.getStringExtra("listid");
//        Toast.makeText(this, listID, Toast.LENGTH_SHORT).show();

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        addNewTaskButton = findViewById(R.id.addNewTaskButton);
        addNewBillButton = findViewById(R.id.addNewBillButton);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddNewItemFabClicked();
                Toast.makeText(v.getContext(), "addNewTaskButton",Toast.LENGTH_SHORT).show();

                Intent details = new Intent(v.getContext(), AddTaskActivity.class);
                details.putExtra("listid",listID);
                startActivityForResult(details,NEW_TASK);
            }
        });
        addNewBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddNewItemFabClicked();
                Toast.makeText(v.getContext(), "addNewBillButton",Toast.LENGTH_SHORT).show();

                Intent details = new Intent(v.getContext(), AddBillActivity.class);
                details.putExtra("listid",listID);
                startActivityForResult(details,NEW_BILL);
            }
        });

        addNewItemFab = findViewById(R.id.itemListFab);
        addNewItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewItemFabClicked();
            }
        });

        mRecyclerView = findViewById(R.id.itemListRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemArrayList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, mItemArrayList);
        mRecyclerView.setAdapter(itemAdapter);

//        @SuppressLint("WrongConstant")
//        SharedPreferences sharedPreferences = this.getSharedPreferences("userDataSharedPref", Context.MODE_APPEND);
//        sharedUserId = sharedPreferences.getString("userid","");

        getItemsFromDatabase();
    }

    public void getItemsFromDatabase()
    {
        GetItemsBackgroundTask getItemsBackgroundTask = new GetItemsBackgroundTask(this);
        getItemsBackgroundTask.execute(listID);
    }

    private void onAddNewItemFabClicked() {
        setVisibility();
        setAnimation();
        setClickable();
        isAddNewItemButtonClicked = !isAddNewItemButtonClicked;
    }

    private void setAnimation() {
        if(!isAddNewItemButtonClicked)
        {
            addNewTaskButton.startAnimation(fromBottom);
            addNewBillButton.startAnimation(fromBottom);
            addNewItemFab.startAnimation(rotateOpen);
        }
        else
        {
            addNewTaskButton.startAnimation(toBottom);
            addNewBillButton.startAnimation(toBottom);
            addNewItemFab.startAnimation(rotateClose);
        }
    }

    private void setVisibility() {
        if(!isAddNewItemButtonClicked)
        {
            addNewTaskButton.setVisibility(View.VISIBLE);
            addNewBillButton.setVisibility(View.VISIBLE);
        }
        else
        {
            addNewTaskButton.setVisibility(View.INVISIBLE);
            addNewBillButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setClickable()
    {
        if(!isAddNewItemButtonClicked)
        {
            addNewTaskButton.setClickable(true);
            addNewBillButton.setClickable(true);
        }
        else
        {
            addNewTaskButton.setClickable(false);
            addNewBillButton.setClickable(false);
        }
    }

    @Override
    public void getLists(ArrayList<Item> arrayOfItems) {
        mItemArrayList.clear();
        mItemArrayList.addAll(arrayOfItems);
        for(int i=0;i<mItemArrayList.size();i++)
            Log.d("Content of mItemArrayList: ",
                    "itemID= "+ mItemArrayList.get(i).getItemID()
                            +", itemName= "+mItemArrayList.get(i).getTitle()
                            +", itemType= "+mItemArrayList.get(i).getType());
        applyItems();
    }

    /**
     * Apply fetched items from database to the view
     */
    private void applyItems() {
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
            itemAdapter.setListItemData(mItemArrayList);
            Log.d("size of array of items: ",String.valueOf(mItemArrayList.size()));
            Log.d("size of item adapter: ",String.valueOf(itemAdapter.getItemCount()));


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
                    Collections.swap(mItemArrayList, from, to);
                    itemAdapter.notifyItemMoved(from, to);
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
                    removeItemFromDatabase(viewHolder, mItemArrayList.get(viewHolder.getAdapterPosition()));
                }
            });

            // Attach the helper to the RecyclerView.
            helper.attachToRecyclerView(mRecyclerView);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_TASK && resultCode == RESULT_OK)
            getItemsFromDatabase();
        else if(requestCode == NEW_BILL && resultCode == RESULT_OK)
            getItemsFromDatabase();
    }

    public void removeItemFromDatabase(RecyclerView.ViewHolder viewHolder, Item item)
    {
        Snackbar.make(itemListRelativeView, "item id: "+item.getItemID()
                                                +", itemname: "+item.getTitle()
                                                +", itemtype: "+item.getType(),
                                                Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
        Log.d("removeListFromDatabase", "item id: "+item.getItemID()
                                                    +", itemname: "+item.getTitle()
                                                    +", itemtype: "+item.getType());

        RemoveItemBackgroundTask removeItemBackgroundTask = new RemoveItemBackgroundTask(this, viewHolder, item);
        removeItemBackgroundTask.execute();
    }

    @Override
    public void removeListFromUI(RecyclerView.ViewHolder viewHolder) {
        // Remove the item from the dataset.
        mItemArrayList.remove(viewHolder.getAdapterPosition());

        // Notify the adapter.
        itemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public void restoreListsFromDb() {
        getItemsFromDatabase();
    }
}
