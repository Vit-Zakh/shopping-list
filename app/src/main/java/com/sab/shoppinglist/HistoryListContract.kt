package com.sab.shoppinglist

import androidx.lifecycle.LiveData
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.coroutines.Job

interface HistoryListContract {
    interface View {
        fun showList(items: List<ShoppingItem>)
        fun updateList(items: List<ShoppingItem>)
    }

    interface Presenter {
        fun getItems(items: List<ShoppingItem>)
        fun addItem(item: ShoppingItem)
        fun deleteItem(item: ShoppingItem)
        suspend fun clearHistory()
    }

    interface Model {
        fun getItems(): LiveData<List<ShoppingItem>>
        suspend fun clearHistory(items: List<ShoppingItem>): Job
    }
}
