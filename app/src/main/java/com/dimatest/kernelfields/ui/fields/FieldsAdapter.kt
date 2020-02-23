package com.dimatest.kernelfields.ui.fields

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.database.entities.FieldDO
import com.dimatest.kernelfields.databinding.ItemFieldBinding
import com.dimatest.kernelfields.utils.click

class FieldsAdapter(private val listener: FieldSelected) : RecyclerView.Adapter<FieldsAdapter.FieldViewHolder>() {

    interface FieldSelected {
        fun selectField(field: FieldDO)
    }

    private val baseContent = mutableListOf<FieldDO>()
    private val content = mutableListOf<FieldDO>()

    var fieldNoDescrFilterList = listOf<String>()
    var fieldNoFilterList = listOf<String>()
    var cornTypeFilterList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val binding: ItemFieldBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_field,
            parent,
            false
        )
        return FieldViewHolder(binding)
    }

    override fun getItemCount() = content.size

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        holder.bind(content[position])
    }

    fun setData(data: List<FieldDO>) {
        baseContent.apply {
            clear()
            addAll(data)
        }
        setList()
    }

    private fun setList() {
        content.apply {
            clear()
            addAll(getFilteredList())
        }
        notifyDataSetChanged()
    }

    fun performSearch(searchQuery: String) {
        content.clear()
        content.addAll(
            if (searchQuery.isEmpty()) getFilteredList()
            else baseContent.filter {
                it.fieldNoDescr?.contains(searchQuery, ignoreCase = true) ?: false
                        || it.fieldNo?.contains(searchQuery, ignoreCase = true) ?: false
                        || it.cornType?.contains(searchQuery, ignoreCase = true) ?: false
            }
        )
        notifyDataSetChanged()
    }

    private fun getFilteredList() : List<FieldDO> {
        if (fieldNoDescrFilterList.isEmpty() && fieldNoFilterList.isEmpty() && cornTypeFilterList.isEmpty())
            return baseContent
        return baseContent.filter {
            if (fieldNoDescrFilterList.isEmpty()) true
            else fieldNoDescrFilterList.contains(it.fieldNoDescr)
        }.filter {
            if (fieldNoFilterList.isEmpty()) true
            else fieldNoFilterList.contains(it.fieldNo)
        }.filter {
            if (cornTypeFilterList.isEmpty()) true
            else cornTypeFilterList.contains(it.cornType)
        }
    }

    inner class FieldViewHolder(private val binding: ItemFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FieldDO) {
            binding.field = item
            itemView click { listener.selectField(item) }
        }
    }
}