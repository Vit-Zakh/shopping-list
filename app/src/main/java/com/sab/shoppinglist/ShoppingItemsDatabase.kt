package com.sab.shoppinglist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sab.shoppinglist.models.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingItemsDatabase : RoomDatabase() {

    abstract fun shoppingItemDao(): ShoppingItemDao

    companion object {
        const val DB_NAME = "ShoppingDatabase"

        @Volatile
        private var INSTANCE: ShoppingItemsDatabase? = null

        fun getInstance(context: Context): ShoppingItemsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): ShoppingItemsDatabase {
            return Room.databaseBuilder(context, ShoppingItemsDatabase::class.java, DB_NAME)
                .build()
        }
    }
}