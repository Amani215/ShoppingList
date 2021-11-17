package com.example.shoppinglist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.ItemDetails
import com.example.shoppinglist.MainActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.Item
import com.example.shoppinglist.databinding.ItemRowBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    var items = mutableListOf<Item>(
        /*Item(null,"Bananas","2",0,200,false),
        Item(null,"Apples","4",0,500,true),
        Item(null,"Car","1",1,1000,false)*/
    )
    val context: Context

    constructor(context: Context, items: List<Item>) {
        this.context = context
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.binding.tvName.text = currentItem.name
        holder.binding.tvDescription.text = currentItem.description
        holder.binding.tvPrice.text = currentItem.estimatedPrice.toString()+"HUF"
        holder.binding.cbStatus.isChecked = currentItem.status

        holder.binding.cbStatus.setOnClickListener {
            items[holder.adapterPosition].status = holder.binding.cbStatus.isChecked
            Thread{
                AppDatabase.getInstance(context).itemDao().updateItem(items[holder.adapterPosition])
            }.start()
        }

        holder.binding.ibDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }

        holder.binding.ibDetails.setOnClickListener{
            var intent = Intent()
            intent.setClass(context,ItemDetails::class.java)
            intent.putExtra("itemId",items[holder.adapterPosition])
            startActivity(context,intent,null)
        }

        if (items[holder.adapterPosition].category == 0) {
            holder.binding.ivIcon.setImageResource(R.drawable.food)
        } else if (items[holder.adapterPosition].category == 1) {
            holder.binding.ivIcon.setImageResource(R.drawable.electronics)
        } else if (items[holder.adapterPosition].category == 2) {
            holder.binding.ivIcon.setImageResource(R.drawable.book)
        } else if (items[holder.adapterPosition].category == 3) {
            holder.binding.ivIcon.setImageResource(R.drawable.other)
        }
    }

    private fun deleteItem(position: Int) {
        Thread{
            AppDatabase.getInstance(context).itemDao().deleteItem(items.get(position))

            (context as MainActivity).runOnUiThread{
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()

    }

    public fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    public fun deleteAllItems(){
        items.removeAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        /*val tvName = binding.tvName
        val tvDescription = binding.tvDescription
        val ivIcon = binding.ivIcon
        val tvPrice = binding.tvPrice
        val cbStatus = binding.cbStatus
        val ibDelete = binding.ibDelete*/
    }
}