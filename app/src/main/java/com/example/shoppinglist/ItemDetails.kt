package com.example.shoppinglist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.Item
import com.example.shoppinglist.databinding.ItemDetailsBinding

class ItemDetails : AppCompatActivity() {
    private lateinit var  binding: ItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var item: Item = intent.getSerializableExtra("itemId") as Item

        binding.tvNameDetail.setText(item.name)
        binding.tvDescriptionDetail.setText(item.description)
        binding.tvCategoryDetail.setText(item.category.toString())
        binding.tvStatusDetail.setText(item.status.toString())
        binding.tvPriceDetail.setText(item.estimatedPrice.toString())
    }
}