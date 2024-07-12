package com.example.fetchcodingassignment.model

/**
 * Class for a list of items.
 *
 * This class defines the item data class and its properties.
 *
 * @property items the items belonging to this list.
 */
class ItemList(var items: MutableList<Item> = mutableListOf()) {

    //Sorts the items numerically from highest to lowest
    fun sortItems() {
        //Remove "Item" prefix from each item name to be sorted properly
        items.forEach {
            item: Item -> val newItemName = item.name.removePrefix("Item ")
            item.name = newItemName
        }

        //Sort the items
        items.sortByDescending { it.name.toInt() }

        //Add "Item" prefix back for each item name
        items.forEach {
            item: Item -> val newItemName = "Item " + item.name
            item.name = newItemName
        }
    }

    //Add an item to the List
    fun addItem(item: Item) {
        items.add(item)
    }
}