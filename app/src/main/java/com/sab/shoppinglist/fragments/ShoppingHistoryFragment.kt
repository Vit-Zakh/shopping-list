package com.sab.shoppinglist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sab.shoppinglist.R
import com.sab.shoppinglist.databinding.FragmentShoppingHistoryBinding

/**
 * A simple [Fragment] subclass.
 */
class ShoppingHistoryFragment : Fragment() {
    private lateinit var historyBinding: FragmentShoppingHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyBinding = FragmentShoppingHistoryBinding.inflate(inflater, container, false)
        return historyBinding.root
    }

}
