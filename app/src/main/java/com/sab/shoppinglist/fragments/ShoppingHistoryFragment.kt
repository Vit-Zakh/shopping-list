package com.sab.shoppinglist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.sab.shoppinglist.viewmodels.HistoryListViewModel

import com.sab.shoppinglist.R
import com.sab.shoppinglist.adapters.HistoryListAdapter
import com.sab.shoppinglist.databinding.FragmentShoppingHistoryBinding
import com.sab.shoppinglist.models.ShoppingItem

/**
 * A simple [Fragment] subclass.
 */
class ShoppingHistoryFragment : Fragment() {
    private lateinit var historyBinding: FragmentShoppingHistoryBinding
    private lateinit var historyListAdapter: HistoryListAdapter

    private var historyList: MutableLiveData<List<ShoppingItem>> = MutableLiveData()
    private lateinit var viewModel: HistoryListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HistoryListViewModel::class.java)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyBinding = FragmentShoppingHistoryBinding.inflate(inflater, container, false)
        historyBinding.historyRecyclerList.apply {
            layoutManager = LinearLayoutManager(context)
            historyListAdapter = HistoryListAdapter()
            adapter = historyListAdapter
            subscribeUi(historyListAdapter)
        }

        historyBinding.deleteBoughtItemsButton.setOnClickListener { viewModel.boughtItems.value?.let { history ->
            viewModel.clearHistory(
                history
            )
        } }

        setHasOptionsMenu(true)

        return historyBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top_history, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Navigation.findNavController(this.requireView()).navigate(R.id.action_shoppingHistoryFragment_to_shoppingListFragment)
        return true
    }

    private fun subscribeUi(adapter: HistoryListAdapter) {
        viewModel.boughtItems.observe(viewLifecycleOwner) { items ->
            adapter.setHistoryList(items)
        }
    }

}
