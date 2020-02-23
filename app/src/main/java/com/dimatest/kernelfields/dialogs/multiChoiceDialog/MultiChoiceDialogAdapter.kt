package com.dimatest.kernelfields.dialogs.multiChoiceDialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.databinding.ItemMultichoiceDialogBinding
import com.dimatest.kernelfields.utils.click

class MultiChoiceDialogAdapter :
    RecyclerView.Adapter<MultiChoiceDialogAdapter.MultiChoiceItemViewHolder>() {

    val content = mutableListOf<MultiChoiceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiChoiceItemViewHolder {
        val binding: ItemMultichoiceDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_multichoice_dialog,
            parent,
            false
        )
        return MultiChoiceItemViewHolder(binding)
    }

    override fun getItemCount() = content.size

    override fun onBindViewHolder(holder: MultiChoiceItemViewHolder, position: Int) {
        holder.bind(content[position])
    }

    fun setList(list: List<MultiChoiceItem>) {
        content.clear()
        content.addAll(list)
        notifyDataSetChanged()
    }

    fun itemChecked(newChecked: Boolean, itemText: String) {
        content.filter { it.text == itemText  }.forEachIndexed { index, item ->
            item.checked = newChecked
            notifyItemChanged(index)
        }
    }

    inner class MultiChoiceItemViewHolder(private val binding: ItemMultichoiceDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MultiChoiceItem) {
            binding.item = item
            binding.itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                itemChecked(isChecked, item.text)
            }
            itemView click { itemChecked(!item.checked, item.text) }
        }
    }
}