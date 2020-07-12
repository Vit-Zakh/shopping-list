package com.sab.shoppinglist

import androidx.lifecycle.LiveData
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class ShoppingItemsRepository (private val shoppingItemDao: ShoppingItemDao){

    val allItems: LiveData<List<ShoppingItem>> = shoppingItemDao.getAllItems()

    val boughtItems: LiveData<List<ShoppingItem>> = shoppingItemDao.getItemsByStatus(true)

//    suspend fun insert(item: ShoppingItem) = shoppingItemDao.insertItem(item)
//
//    suspend fun delete(item: ShoppingItem) = shoppingItemDao.deleteItem(item)
//
//    suspend fun update(item: ShoppingItem) = shoppingItemDao.updateItem(item)

    suspend fun insert(item: ShoppingItem){
        withContext(IO){
        shoppingItemDao.insertItem(item)}
    }
//
//    suspend fun insertAll(items: List<ShoppingItem>){
//        withContext(IO){
//        shoppingItemDao.insertAll(items)
//    }}
//
    suspend fun delete(item: ShoppingItem){
        shoppingItemDao.deleteItem(item)
    }
    suspend fun deleteBoughtItems(items: List<ShoppingItem>){
        shoppingItemDao.deleteHistory(items)
    }
    suspend fun update(item: ShoppingItem){
        shoppingItemDao.updateItem(item)
    }
//    suspend fun getBoughtItems(): LiveData<List<ShoppingItem>> =
//        shoppingItemDao.getItemsByStatus(true)
//
//    suspend fun getAllItems(): LiveData<List<ShoppingItem>>{
//       return withContext(IO) {
//            shoppingItemDao.getAllItems()
//        }
//    }


//    companion object {
//
//        // For Singleton instantiation
//        @Volatile private var instance: ShoppingItemsRepository? = null
//
//        fun getInstance(shoppingItemDao: ShoppingItemDao) =
//            instance ?: synchronized(this) {
//                instance ?: ShoppingItemsRepository(shoppingItemDao).also { instance = it }
//            }
//    }

}