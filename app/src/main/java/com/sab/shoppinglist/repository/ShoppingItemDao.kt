package com.sab.shoppinglist.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sab.shoppinglist.models.ShoppingItem


@Dao
interface ShoppingItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ShoppingItem>)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Delete
    suspend fun deleteHistory(items: List<ShoppingItem>)

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Query("SELECT * FROM ShoppingItem WHERE status =:status")
    fun getItemsByStatus(status: Boolean): LiveData<List<ShoppingItem>>

    @Query("SELECT * FROM ShoppingItem")
    fun getAllItems(): LiveData<List<ShoppingItem>>


}