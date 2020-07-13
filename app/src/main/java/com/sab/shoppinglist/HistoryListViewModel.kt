package com.sab.shoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryListViewModel (application: Application) : AndroidViewModel(application){
    private val repository: ShoppingItemsRepository
    val boughtItems: LiveData<List<ShoppingItem>>

    init {
        val shoppingItemDao = ShoppingItemsDatabase.getInstance(application).shoppingItemDao()
        repository = ShoppingItemsRepository(shoppingItemDao)
        boughtItems = repository.boughtItems

    }

    fun addItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) { repository.insert(item) }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) { repository.delete(item) }

    fun clearHistory(items: List<ShoppingItem>) = viewModelScope.launch(Dispatchers.IO) { repository.deleteBoughtItems(items) }
}