package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.Item
import com.example.shoppinglist.databinding.ItemRowBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    var items = mutableListOf<Item>(
        Item(null,"Bananas","2",0,false),
        Item(null,"Apples","4",0,true),
        Item(null,"Car","1",1,false)
    )
    val context: Context

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTodo = items[position]

        holder.binding.tvDate.text = currentTodo.name
        holder.binding.cbDone.text = currentTodo.description
        holder.binding.cbDone.isChecked = currentTodo.status

        holder.binding.btnDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }

        if (items[holder.adapterPosition].category == 0) {
            holder.binding.ivIcon.setImageResource(R.mipmap.ic_launcher)
        } else if (items[holder.adapterPosition].category == 1) {
            //holder.binding.ivIcon.setImageResource(R.drawable.todohead)
        }
    }

    private fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    public fun addItem(todo: Item) {
        items.add(todo)
        notifyItemInserted(items.lastIndex)
    }

    public fun updateItem(item: Item, editIndex: Int) {
        items.set(editIndex, item)
        notifyItemChanged(editIndex)
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvDate = binding.tvDate
        val cbDone = binding.cbDone
        val btnDelete = binding.btnDelete
        val btnEdit = binding.btnEdit
        val ivIcon = binding.ivIcon
    }
}