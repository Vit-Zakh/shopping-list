package com.sab.shoppinglist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sab.shoppinglist.R
import com.sab.shoppinglist.databinding.LayoutShoppingItemBinding
import com.sab.shoppinglist.models.ShoppingItem
import kotlinx.android.synthetic.main.layout_shopping_item.view.*

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ItemHolder>() {

    private var shoppingList: List<ShoppingItem> = ArrayList()

    class ItemHolder constructor(val shoppingItemBinding: LayoutShoppingItemBinding) :
        RecyclerView.ViewHolder(shoppingItemBinding.root) {

        fun bind(item: ShoppingItem) = with(shoppingItemBinding.root) {
            this.itemTitle.text = item.title
            this.itemQuantity.text = "x${item.amount}"
            this.checkBox.isChecked = item.isChecked
            this.checkBox.setOnCheckedChangeListener { _, _ ->
                item.isChecked = this.checkBox.isChecked
                Log.d("CheckTag", "bind: ${item.title} is ${item.isChecked}")
            }
            Glide.with(context).load(item.imageUrl)
                .placeholder(R.drawable.ic_shopping_cart_black_128dp).centerCrop()
                .into(this.itemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            shoppingItemBinding = LayoutShoppingItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(shoppingList[position])
    }

    fun setShoppingList(list: List<ShoppingItem>) {
        shoppingList = list
        notifyDataSetChanged()
    }
}
