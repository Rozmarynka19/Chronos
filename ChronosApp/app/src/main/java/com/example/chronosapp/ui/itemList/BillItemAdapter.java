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

package com.example.chronosapp.ui.itemList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chronosapp.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the data about specific list.
 */
class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<BillItem> billItemArrayList;
    private Context mContext;

    /**
     * Constructor that passes in the list item data and the context.
     *
     * @param billItemArrayList ArrayList containing the list item data.
     * @param context Context of the application.
     */
    BillItemAdapter(Context context, ArrayList<BillItem> billItemArrayList) {
        this.billItemArrayList = billItemArrayList;
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
    public BillItemAdapter.ViewHolder onCreateViewHolder(
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
    public void onBindViewHolder(BillItemAdapter.ViewHolder holder,
                                 int position) {
        // Get current list item.
        BillItem currentBillItem = billItemArrayList.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentBillItem);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return billItemArrayList.size();
    }

    /**
     * Setting current lists
     *
     * @param lists  Array of current lists.
     */
    public void setListItemData(ArrayList<BillItem> lists){
        billItemArrayList = lists;
        this.notifyDataSetChanged();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
//        private TextView mDescriptionText;
        private ImageView mSportsImage;

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

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(BillItem currentBillItem){
            // Populate the textviews with data.
            mTitleText.setText(currentBillItem.getTitle());
//            mDescriptionText.setText(currentListItem.getDescription());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentBillItem.getImageResource()).into(mSportsImage);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
//            ListItem currentListItem = mListItemData.get(getAdapterPosition());
//            Intent detailIntent = new Intent(mContext, DetailActivity.class);
//            detailIntent.putExtra("title", currentListItem.getTitle());
//            detailIntent.putExtra("image_resource",
//                    currentListItem.getImageResource());
//            mContext.startActivity(detailIntent);
        }
    }
}
