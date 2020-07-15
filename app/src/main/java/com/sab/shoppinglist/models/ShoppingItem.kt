package com.sab.shoppinglist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "item_title") var title: String? = null,
    @ColumnInfo(name = "image_url") var imageUrl: String? = null,
    @ColumnInfo(name = "item_quantity") var amount: Int = 1,
    @ColumnInfo(name = "status") var isBought: Boolean = false,
    @ColumnInfo(name = "bought_ago") var boughtAgo: Long = 0,
    @Ignore var isChecked:Boolean = false){}