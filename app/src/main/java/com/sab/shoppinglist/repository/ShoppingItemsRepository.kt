package com.sab.shoppinglist.repository

import androidx.lifecycle.LiveData
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class ShoppingItemsRepository (private val shoppingItemDao: ShoppingItemDao){

    val allItems: LiveData<List<ShoppingItem>> = shoppingItemDao.getAllItems()

    val boughtItems: LiveData<List<ShoppingItem>> = shoppingItemDao.getItemsByStatus(true)

    suspend fun insert(item: ShoppingItem){
        withContext(IO){
        shoppingItemDao.insertItem(item)}
    }

    suspend fun delete(item: ShoppingItem){
        shoppingItemDao.deleteItem(item)
    }
    suspend fun deleteBoughtItems(items: List<ShoppingItem>){
        shoppingItemDao.deleteHistory(items)
    }
    suspend fun update(item: ShoppingItem){
        shoppingItemDao.updateItem(item)
    }


}