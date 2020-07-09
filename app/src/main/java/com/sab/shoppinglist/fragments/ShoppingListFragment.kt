package com.sab.shoppinglist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sab.shoppinglist.R
import com.sab.shoppinglist.adapters.ShoppingListAdapter
import com.sab.shoppinglist.databinding.FragmentShoppingListBinding
import com.sab.shoppinglist.models.TestData

/**
 * A simple [Fragment] subclass.
 */
class ShoppingListFragment : Fragment() {
    private lateinit var listBinding: FragmentShoppingListBinding
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listBinding = FragmentShoppingListBinding.inflate(inflater, container,false)
        listBinding.shoppingRecyclerList.apply {
            layoutManager = LinearLayoutManager(context)
            shoppingListAdapter = ShoppingListAdapter()
            shoppingListAdapter.setShoppingList(TestData.createTestItemsList())
            adapter = shoppingListAdapter
        }

        setHasOptionsMenu(true)

        return listBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}
