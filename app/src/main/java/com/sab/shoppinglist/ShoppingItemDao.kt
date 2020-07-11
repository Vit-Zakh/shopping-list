package com.sab.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sab.shoppinglist.models.ShoppingItem


@Dao
interface ShoppingItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: ShoppingItem)

    @Delete
    fun deleteItem(item: ShoppingItem)

    @Update
    fun updateItem(item: ShoppingItem)

    @Query("SELECT * FROM ShoppingItem WHERE status =:status")
    fun getItemsByStatus(status: Boolean): LiveData<List<ShoppingItem>>

    @Query("SELECT * FROM ShoppingItem")
    fun getAllItems(): LiveData<List<ShoppingItem>>


}