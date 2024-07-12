package com.example.fetchcodingassignment.model

/**
 * Data class for an item.
 *
 * This class defines the item data class and its properties.
 *
 * @property id the id number of this item.
 * @property listId the list id this item belongs to
 * @property name the name of this item.
 */
data class Item(val id: Int, val listId: Int, var name: String)