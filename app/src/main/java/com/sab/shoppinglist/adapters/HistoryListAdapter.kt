package com.sab.shoppinglist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.sab.shoppinglist.R
import com.sab.shoppinglist.databinding.LayoutBoughtItemBinding
import com.sab.shoppinglist.databinding.LayoutShoppingItemBinding
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.android.synthetic.main.layout_bought_item.view.*
import kotlinx.android.synthetic.main.layout_shopping_item.view.*
import kotlinx.android.synthetic.main.layout_shopping_item.view.itemImage
import kotlinx.android.synthetic.main.layout_shopping_item.view.itemQuantity
import kotlinx.android.synthetic.main.layout_shopping_item.view.itemTitle

class HistoryListAdapter : RecyclerView.Adapter<HistoryListAdapter.ItemHolder>() {

    private var historyList: List<ShoppingItem> = ArrayList()

    class ItemHolder constructor(val boughtItemBinding: LayoutBoughtItemBinding) :
        RecyclerView.ViewHolder(boughtItemBinding.root) {

        fun bind(item: ShoppingItem) = with(boughtItemBinding.root) {
            this.itemTitle.text = item.title
            this.itemQuantity.text = "x${item.amount}"
            this.wasBoughtTime.text = TimeAgo.using(item.boughtAgo)
            Glide.with(context).load(item.imageUrl)
                .placeholder(R.drawable.ic_shopping_cart_black_128dp)
                .centerCrop().into(this.itemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            boughtItemBinding = LayoutBoughtItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(historyList[position])
    }

    fun setHistoryList(list: List<ShoppingItem>) {
        historyList = list
        notifyDataSetChanged()
    }
}