package com.example.shoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.shoppinglist.data.Item
import com.example.shoppinglist.databinding.ItemDialogBinding

class ItemDialog : DialogFragment() {
    lateinit var etItemName: EditText
    lateinit var etItemDescription: EditText
    lateinit var cbItemStatus: CheckBox
    lateinit var spinnerCategory: Spinner
    lateinit var etItemPrice: EditText

    interface ItemHandler{
        fun itemCreated(item: Item)
        fun itemUpdated(item: Item)
    }

    lateinit var itemHandler: ItemHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ItemHandler){
            itemHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the ItemHandler interface.")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Item dialog")
        val dialogBinding = ItemDialogBinding.inflate(layoutInflater)

        etItemName = dialogBinding.etItemName
        etItemDescription = dialogBinding.etItemDescription
        cbItemStatus = dialogBinding.cbItemStatus
        spinnerCategory = dialogBinding.spinnerCategory
        etItemPrice = dialogBinding.etItemPrice

        var categoryAdapter = ArrayAdapter.createFromResource(
            requireContext()!!,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerCategory.adapter = categoryAdapter


        dialogBuilder.setView(dialogBinding.root)

        val arguments = this.arguments

        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->
        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etItemName.text.isNotEmpty()) {
                val arguments = this.arguments
                    handleItemCreate()

                dialog!!.dismiss()
            } else {
                etItemName.error = "This field can not be empty"
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_dialog, container, false)
    }

    private fun handleItemCreate() {
        itemHandler.itemCreated(
            Item(
                null,
                etItemName.text.toString(),
                etItemDescription.text.toString(),
                spinnerCategory.selectedItemPosition,
                etItemPrice.text.toString().toInt(),
                false
            )
        )
    }
}