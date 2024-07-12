package com.example.fetchcodingassignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fetchcodingassignment.model.Item
import com.example.fetchcodingassignment.model.ItemList
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.StringReader
import java.net.URL

data class ListUiState(
    val listsByID: HashMap<Int, ItemList> = HashMap()
)

/**
 * Class: ItemListViewmodel
 *
 * This class is the ViewModel for the list of Items. When this ViewModel is created it will connect
 * to the URL, pull the JSON file information, and parse it to usable data within the application
 *
 * The final list of items by ID is stored as a Hashmap where key is the list ID and value is the list of items
 * belonging to that list ID
 */
class ItemListViewmodel: ViewModel() {
    private val _uiState = MutableStateFlow(ListUiState())
    var listsByID: HashMap<Int, ItemList> = HashMap()

    fun PopulateListsWithData() {
        val allItemsFromJSON = GrabDataFromURL("https://fetch-hiring.s3.amazonaws.com/hiring.json")
        ParseListofItems(allItemsFromJSON)

        _uiState.update { currentState ->
            currentState.copy(
                listsByID = this.listsByID
            )
        }
    }

    //Pulls JSON data from URL and formats to a List of Item objects
    fun GrabDataFromURL(url: String): List<Item> {
        try {
            val jsonStr = URL(url).readText()
            val stringReader = StringReader(jsonStr)
            val gson = Gson()
            return gson.fromJson(stringReader, Array<Item>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //If URL invalid return an empty list
        return emptyList()
    }

    fun ParseListofItems(allItems: List<Item>) {
        for (item in allItems) {
            try {
                //don't include item if name is blank or null
                if (item.name.equals("") || item.name.equals("null")) {
                    Log.d("Item Status", "Blank name, Item discarded")
                }
                else {
                    //If list is empty, add the first ItemList and assign the item to it
                    if (listsByID.isEmpty()) {
                        val itemList = ItemList()
                        itemList.addItem(item)
                        listsByID[item.listId] = itemList
                    }
                    else {
                        //If list exists, add the item to it
                        if (listsByID[item.listId] != null) {
                            var currentList = listsByID[item.listId]
                            Log.d("Item Status", "New Item Added to Existing List")
                            if (currentList != null) {
                                currentList.addItem(item)
                                listsByID[item.listId] = currentList
                            }
                        }
                        //If list does not exist, create it and add the item to it
                        else {
                            val itemList = ItemList()
                            itemList.addItem(item)
                            listsByID[item.listId] = itemList
                        }
                    }
                    Log.d("Item Status", "Item added")
                }
            //If an error occurs while formatting data, Item must be null and shouldn't be added
            } catch (e: Exception) {
                Log.d("Item Status", "Null Item")
            }
        }

        //Sort item lists at the end of data parsing
        var x = 1
        while (x <= listsByID.size) {
            listsByID[x]?.sortItems()
            Log.d("List ID", listsByID.get(x)?.items.toString())
            x++
        }
    }
}

