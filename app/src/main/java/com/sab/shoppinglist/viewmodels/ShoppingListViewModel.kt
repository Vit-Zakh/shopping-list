package com.sab.shoppinglist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sab.shoppinglist.models.ShoppingItem
import com.sab.shoppinglist.repository.ShoppingItemsDatabase
import com.sab.shoppinglist.repository.ShoppingItemsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ShoppingListViewModel(application: Application) : AndroidViewModel(application){
    private val repository: ShoppingItemsRepository
    val allItems: LiveData<List<ShoppingItem>>
    val boughtItems: LiveData<List<ShoppingItem>>

    init {
        val shoppingItemDao = ShoppingItemsDatabase.getInstance(application).shoppingItemDao()
        repository = ShoppingItemsRepository(
            shoppingItemDao
        )
        allItems = repository.allItems
        boughtItems = repository.boughtItems
    }

    fun addItem(item: ShoppingItem) = viewModelScope.launch(IO) { repository.insert(item) }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch(IO) { repository.delete(item) }

    fun updateItem(item: ShoppingItem) = viewModelScope.launch(IO) { repository.update(item) }
}