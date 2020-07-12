package com.sab.shoppinglist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sab.shoppinglist.models.ShoppingItem
import com.sab.shoppinglist.models.TestData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Database(entities = arrayOf(ShoppingItem::class), version = 1, exportSchema = false)
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
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(IO).launch {
                            val database = getInstance(context)
                            database.shoppingItemDao().insertAll(TestData.createTestItemsList())
                        }

                    }
                })
                .build()
        }
    }
}