package com.example.shoppinglist.data

data class Item(
    var itemID: Long?,
    var name: String,
    var description: String,
    var category: Int,
    var estimatedPrice: Int,
    var status: Boolean
)
