package com.sab.shoppinglist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sab.shoppinglist.R
import com.sab.shoppinglist.databinding.FragmentShoppingListBinding

/**
 * A simple [Fragment] subclass.
 */
class ShoppingListFragment : Fragment() {
    private lateinit var listBinding: FragmentShoppingListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listBinding = FragmentShoppingListBinding.inflate(inflater, container,false)
        return listBinding.root
    }

}
