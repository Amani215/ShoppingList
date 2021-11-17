package com.example.shoppinglist

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.shoppinglist.adapter.ItemAdapter
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.Item
import com.example.shoppinglist.databinding.ActivityScrollingBinding

class MainActivity : AppCompatActivity(), ItemDialog.ItemHandler{

    private lateinit var binding: ActivityScrollingBinding
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            ItemDialog().show(supportFragmentManager, "Dialog")
        }

        Thread{
            var items = AppDatabase.getInstance(this).itemDao().getAllItems()

            runOnUiThread{
                itemAdapter = ItemAdapter(this, items)
                binding.rvItems.adapter = itemAdapter

                val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                binding.rvItems.addItemDecoration(itemDecoration)
            }
        }.start()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_details -> {
                deleteAllItems()
                Toast.makeText(applicationContext, "List deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun itemCreated(item: Item) {
        Thread{
            item.itemID = AppDatabase.getInstance(this).itemDao().insertItem(item)

            runOnUiThread {
                itemAdapter.addItem(item)
            }
        }.start()
    }

    fun deleteAllItems(){
        Thread{
            AppDatabase.getInstance(this).itemDao().deleteAllItems()

            runOnUiThread{
                itemAdapter.deleteAllItems()
            }
        }.start()
    }
}