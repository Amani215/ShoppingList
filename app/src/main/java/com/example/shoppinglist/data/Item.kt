package com.example.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) var itemID: Long?,
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "Description") var description: String,
    @ColumnInfo(name = "CategoryNum") var category: Int,
    @ColumnInfo(name = "EstimatedPrice") var estimatedPrice: Int,
    @ColumnInfo(name = "Status") var status: Boolean
): Serializable
