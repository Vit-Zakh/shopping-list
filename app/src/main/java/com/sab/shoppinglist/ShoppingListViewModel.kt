package com.sab.shoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class ShoppingListViewModel internal constructor(shoppingItemsRepository: ShoppingItemsRepository
//) : ViewModel(){
//
//    val items: LiveData<List<ShoppingItem>> = shoppingItemsRepository.getAllItems()
//
//
//
//}

class ShoppingListViewModel(application: Application) : AndroidViewModel(application){
    private val repository: ShoppingItemsRepository
    val allItems: LiveData<List<ShoppingItem>>
    val boughtItems: LiveData<List<ShoppingItem>>

    init {
        val shoppingItemDao = ShoppingItemsDatabase.getInstance(application).shoppingItemDao()
        repository = ShoppingItemsRepository(shoppingItemDao)
        allItems = repository.allItems
        boughtItems = repository.boughtItems

    }

//    suspend fun addItem(item: ShoppingItem) = repository.insert(item)
//
//    suspend fun deleteItem(item: ShoppingItem) = repository.delete(item)

    fun addItem(item: ShoppingItem) = viewModelScope.launch(IO) { repository.insert(item) }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch(IO) { repository.delete(item) }

    fun updateItem(item: ShoppingItem) = viewModelScope.launch(IO) { repository.update(item) }
}