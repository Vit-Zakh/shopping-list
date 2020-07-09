package com.sab.shoppinglist.models

class TestData {
    companion object{
        fun createTestItemsList(): ArrayList<ShoppingItem>{
            val itemsList = ArrayList<ShoppingItem>()
            itemsList.add(
                ShoppingItem(title = "Квас")
            )
            itemsList.add(
                ShoppingItem(title = "Йогурт")
            )
            itemsList.add(
                ShoppingItem(title = "Кукурузка")
            )
            itemsList.add(
                ShoppingItem(title = "Бананы")
            )
            itemsList.add(
                ShoppingItem(title = "Мыло")
            )
            itemsList.add(
                ShoppingItem(title = "Орешки")
            )
            itemsList.add(
                ShoppingItem(title = "Корм для кошки")
            )


            return itemsList
        }
    }
}