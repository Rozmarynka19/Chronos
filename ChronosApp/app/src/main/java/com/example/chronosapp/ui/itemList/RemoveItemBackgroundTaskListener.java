package com.example.chronosapp.ui.itemList;

import androidx.recyclerview.widget.RecyclerView;

public interface RemoveItemBackgroundTaskListener {
    void removeListFromUI(RecyclerView.ViewHolder viewHolder);
    void restoreListsFromDb();
}
