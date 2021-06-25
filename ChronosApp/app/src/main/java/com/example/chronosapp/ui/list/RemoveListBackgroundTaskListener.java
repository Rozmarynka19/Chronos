package com.example.chronosapp.ui.list;

import androidx.recyclerview.widget.RecyclerView;

public interface RemoveListBackgroundTaskListener {
    void removeListFromUI(RecyclerView.ViewHolder viewHolder);
    void restoreListsFromDb();
}
