package com.sab.shoppinglist.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.sab.shoppinglist.R
import com.sab.shoppinglist.ShoppingItemsDatabase
import com.sab.shoppinglist.ShoppingItemsRepository
import com.sab.shoppinglist.adapters.ShoppingListAdapter
import com.sab.shoppinglist.databinding.FragmentShoppingListBinding
import com.sab.shoppinglist.databinding.LayoutNewShoppingItemBinding
import com.sab.shoppinglist.models.ShoppingItem
import com.sab.shoppinglist.models.TestData

import kotlinx.android.synthetic.main.layout_new_shopping_item.*

/**
 * A simple [Fragment] subclass.
 */
class ShoppingListFragment : Fragment() {
    private lateinit var listBinding: FragmentShoppingListBinding
    private lateinit var shoppingListAdapter: ShoppingListAdapter
//    private lateinit var repository: ShoppingItemsRepository
    private lateinit var database: ShoppingItemsDatabase
    private var shoppingList: MutableLiveData<List<ShoppingItem>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
//        repository = ShoppingItemsRepository(shoppingItemsDatabase.shoppingItemDao())
        database = ShoppingItemsDatabase.getInstance(requireContext())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listBinding = FragmentShoppingListBinding.inflate(inflater, container,false)
        listBinding.shoppingRecyclerList.apply {
            layoutManager = LinearLayoutManager(context)
            shoppingListAdapter = ShoppingListAdapter()
            //shoppingListAdapter.setShoppingList(TestData.createTestItemsList())

            adapter = shoppingListAdapter
        }
        listBinding.addItemFloatingButton.setOnClickListener { showAddItemDialog() }

        setHasOptionsMenu(true)

        return listBinding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showAddItemDialog() {

        val newShoppingItemBinding = LayoutNewShoppingItemBinding.inflate(layoutInflater)

        val addItemDialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.layout_new_shopping_item, newShoppingItemBinding.root)

        newShoppingItemBinding.cancelButton.setOnClickListener { addItemDialog.dismiss() }
        //stub here TODO: remove the stub
        var number = 0
        newShoppingItemBinding.addItemButton.setOnClickListener { Toast.makeText(context, "item added $number",
            Toast.LENGTH_SHORT).show() }
        newShoppingItemBinding.newItemQuantityPicker.minValue = 1
        newShoppingItemBinding.newItemQuantityPicker.maxValue = 99
        newShoppingItemBinding.newItemQuantityPicker
            .setOnValueChangedListener { numberPicker, oldVar, newVar ->
            number = newVar
        }






        addItemDialog.show()

    }

}
