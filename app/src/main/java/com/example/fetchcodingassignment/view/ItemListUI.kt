package com.example.fetchcodingassignment.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fetchcodingassignment.model.Item
import com.example.fetchcodingassignment.viewmodel.ItemListViewmodel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ItemListScreen(viewModel: ItemListViewmodel) {
    val allItems = viewModel.listsByID

    LazyColumn {
        allItems.forEach {(key, value) ->
            if (allItems[key] != null) {
                item { ListHeader(listID = key)}
                items(allItems[key]!!.items) {item -> ItemCard(item = item)}
            }
        }
    }
}

@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    Card (colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.padding(4.dp)) {
        Row (modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = item.name,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp))
            Text(text = "ID#" + item.id.toString(),
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun ListHeader(listID: Int, modifier: Modifier = Modifier) {
    Card (colors = CardDefaults.cardColors(containerColor =  MaterialTheme.colorScheme.onSurface),
        modifier = modifier.padding(4.dp).fillMaxSize(), ) {
        Text(text = "List ID#" + listID.toString(),
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            color = Color.White,
            modifier = Modifier.padding(32.dp).fillMaxSize())
    }
}