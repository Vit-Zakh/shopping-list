package com.sab.shoppinglist

import androidx.lifecycle.LiveData
import com.sab.shoppinglist.models.ShoppingItem


class ShoppingItemsRepository(private val shoppingItemDao: ShoppingItemDao) {

    fun insert(item: ShoppingItem){
        shoppingItemDao.insertItem(item)
    }
    fun delete(item: ShoppingItem){
        shoppingItemDao.deleteItem(item)
    }
    fun update(item: ShoppingItem){
        shoppingItemDao.updateItem(item)
    }
    fun getBoughtItems(): LiveData<List<ShoppingItem>> =
        shoppingItemDao.getItemsByStatus(true)

    fun getAllItems(): LiveData<List<ShoppingItem>> =
        shoppingItemDao.getAllItems()

}