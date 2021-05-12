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

/**
 * Data model for each row of the RecyclerView
 */
class BillItem {
    // Member variables representing the list title and its description.
    private String type="bill", title, itemID;
    private final int imageResource;

    /**
     * Constructor for the ListItem data model.
     *
     * @param title The list name.
     * @param itemID List ID.
     * @param imageResource Background image
     */
    public BillItem(String title, String itemID, int imageResource) {
        this.title = title;
        this.itemID = itemID;
        this.imageResource = imageResource;
    }

    /**
     * Gets the list title.
     *
     * @return List title
     */
    String getTitle() {
        return title;
    }

    /**
     * Gets the item id.
     *
     * @return item id.
     */
    String getItemID() {
        return itemID;
    }

    /***
     * Gets the background image
     *
     * @return Background image
     */
    public int getImageResource() {
        return imageResource;
    }

}
