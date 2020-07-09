package com.sab.shoppinglist

import androidx.room.*
import com.sab.shoppinglist.models.ShoppingItem

@Dao
interface ShoppingItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Query("SELECT * FROM ShoppingItem WHERE status =:status")
    suspend fun getItemsByStatus(status: Boolean): List<ShoppingItem>

    @Query("SELECT * FROM ShoppingItem")
    suspend fun getAllItems(): List<ShoppingItem>


}