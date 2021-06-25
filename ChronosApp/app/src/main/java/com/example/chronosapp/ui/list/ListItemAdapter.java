/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.chronosapp.ui.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chronosapp.R;
import com.example.chronosapp.ui.itemList.ListOfItemsMainActivity;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the data about specific list.
 */
class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<ListItem> mListItemData;
    private Context mContext;

    /**
     * Constructor that passes in the list item data and the context.
     *
     * @param listItemData ArrayList containing the list item data.
     * @param context Context of the application.
     */
    ListItemAdapter(Context context, ArrayList<ListItem> listItemData) {
        this.mListItemData = listItemData;
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(ListItemAdapter.ViewHolder holder,
                                 int position) {
        // Get current list item.
        ListItem currentListItem = mListItemData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentListItem);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mListItemData.size();
    }

    /**
     * Setting current lists
     *
     * @param lists  Array of current lists.
     */
    public void setListItemData(ArrayList<ListItem> lists){
        mListItemData = lists;
        this.notifyDataSetChanged();
    }

    public String getTitleByListId(int listId)
    {
        Log.d("getTitleByListId - arg: listId",String.valueOf(listId));
        for(ListItem mList : mListItemData)
        {
            Log.d("getTitleByListId","listId: "+mList.getListID()+", title: "+mList.getTitle());
            if(mList.getListID().equals(String.valueOf(listId)))
                return mList.getTitle();
        }

        return null;
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
//        private TextView mDescriptionText;
        private ImageView mSportsImage;
        private CardView mCardView;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.titleOnBackground);
//            mDescriptionText = itemView.findViewById(R.id.listDescription);
            mSportsImage = itemView.findViewById(R.id.backgroundImage);
            mCardView = itemView.findViewById(R.id.cardView);
            mCardView.setOnCreateContextMenuListener(this);


            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(ListItem currentListItem){
            // Populate the textviews with data.
            mTitleText.setText(currentListItem.getTitle());
//            mDescriptionText.setText(currentListItem.getDescription());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentListItem.getImageResource()).into(mSportsImage);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            ListItem currentListItem = mListItemData.get(getAdapterPosition());
//            Intent detailIntent = new Intent(mContext, DetailActivity.class);
//            detailIntent.putExtra("title", currentListItem.getTitle());
//            detailIntent.putExtra("image_resource",
//                    currentListItem.getImageResource());
//            mContext.startActivity(detailIntent);
            Intent details = new Intent(mContext, ListOfItemsMainActivity.class);
            details.putExtra("listid",currentListItem.getListID());
            mContext.startActivity(details);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(mTitleText.getText());
            String currentListId = mListItemData.get(getAdapterPosition()).getListID();
            menu.add(Integer.parseInt(currentListId), 1, 100, "Rename the list");
        }
    }
}
