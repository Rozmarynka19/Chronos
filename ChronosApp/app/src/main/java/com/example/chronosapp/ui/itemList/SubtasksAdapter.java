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
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chronosapp.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the data about specific list.
 */
class SubtasksAdapter extends RecyclerView.Adapter<SubtasksAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<String> mSubtasks;
    private Context mContext;

    /**
     * Constructor that passes in the list item data and the context.
     *
     * @param mSubtasks ArrayList containing the list item data.
     * @param context Context of the application.
     */
    SubtasksAdapter(Context context, ArrayList<String> mSubtasks) {
        this.mSubtasks = mSubtasks;
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
    public SubtasksAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.subtask, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(SubtasksAdapter.ViewHolder holder,
                                 int position) {
        // Get current list item.
        String currentSubtaskName = mSubtasks.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentSubtaskName);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSubtasks.size();
    }

    /**
     * Setting current lists
     *
     * @param subtasks  Array of subtasks.
     */
    public void setSubtasks(ArrayList<String> subtasks){
        mSubtasks = subtasks;
        this.notifyDataSetChanged();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView subtaskName;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the subtasks.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            subtaskName = itemView.findViewById(R.id.subtaskName);
        }

        void bindTo(String currentSubtaskName){
            // Populate the textviews with data.
            subtaskName.setText(currentSubtaskName);
        }
    }
}
