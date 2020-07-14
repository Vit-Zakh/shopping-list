package com.sab.shoppinglist

import com.sab.shoppinglist.models.ShoppingItem
import com.sab.shoppinglist.viewmodels.HistoryListViewModel

class HistoryListPresenter(historyListViewModel: HistoryListViewModel) :
    HistoryListContract.Presenter {

    private val viewModel = historyListViewModel


    override fun getItems(items: List<ShoppingItem>) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: ShoppingItem) {
        TODO("Not yet implemented")
    }

    override fun deleteItem(item: ShoppingItem) {
        TODO("Not yet implemented")
    }

    override suspend fun clearHistory() {
        viewModel.boughtItems.value?.let { history ->
            viewModel.clearHistory(
                history
            )
        }
    }

}
