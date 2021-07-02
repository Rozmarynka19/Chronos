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
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosapp.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the data about specific list.
 */
class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<String> mAttachments;
    private Context mContext;

    /**
     * Constructor that passes in the list item data and the context.
     *
     * @param mAttachments ArrayList containing the list item data.
     * @param context Context of the application.
     */
    AttachmentsAdapter(Context context, ArrayList<String> mAttachments) {
        this.mAttachments = mAttachments;
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
    public AttachmentsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.attachment, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(AttachmentsAdapter.ViewHolder holder,
                                 int position) {
        // Get current list item.
        String currentAttachmentName = mAttachments.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentAttachmentName);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mAttachments.size();
    }

    /**
     * Setting current lists
     *
     * @param mAttachments  Array of subtasks.
     */
    public void setAttachments(ArrayList<String> mAttachments){
        this.mAttachments = mAttachments;
        this.notifyDataSetChanged();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView attachmentName;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the subtasks.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            attachmentName = itemView.findViewById(R.id.attachmentName);
        }

        void bindTo(String currentAttachmentName){
            // Populate the textviews with data.
            attachmentName.setText(currentAttachmentName);
        }
    }
}
