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
class Item {
    // Member variables representing the list title and its description.
    /*
    type = "task" | "bill"
        imageResource (background) depends on item's type
        if type=="task"
            imageResource is given depending on task's piority
        if type=="bill"
            another imageResource to indicate to user that's bill
     */
    private String type, title, itemID;
    private final int imageResource;

    /**
     * Constructor for the ListItem data model.
     *
     * @param title The list name.
     * @param itemID item ID.
     * @param imageResource Background image
     */
    public Item(String title, String itemID, String type, int imageResource) {
        this.title = title;
        this.itemID = itemID;
        this.type = type;
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
     * Gets the list id.
     *
     * @return List id.
     */
    String getItemID() {
        return itemID;
    }

    /**
     * Gets the item type.
     *
     * @return Item type.
     */
    String getType() {return type;}

    /***
     * Gets the background image
     *
     * @return Background image
     */
    public int getImageResource() {
        return imageResource;
    }

}
