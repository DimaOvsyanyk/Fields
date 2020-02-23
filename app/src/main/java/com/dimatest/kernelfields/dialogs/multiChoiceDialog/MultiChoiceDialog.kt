package com.dimatest.kernelfields.dialogs.multiChoiceDialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.common.BaseFullScreenDialog
import com.dimatest.kernelfields.databinding.DialogMultichoiceBinding
import com.dimatest.kernelfields.utils.click

class MultiChoiceDialog : BaseFullScreenDialog<DialogMultichoiceBinding>() {

    interface FilterSelectedListener {
        fun newFiltersSelected(filterList: List<String>)
    }

    private lateinit var listener: FilterSelectedListener

    override fun getLayoutRes() = R.layout.dialog_multichoice

    private val multiChoiceAdapter: MultiChoiceDialogAdapter by lazy {
        MultiChoiceDialogAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        setupListeners()
        setupLists()
    }

    private fun setupLists() {
        binding.itemsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = multiChoiceAdapter
        }
    }

    private fun setupListeners() {
        binding.multiChoiceDialogOkCancel click { dismiss() }
        binding.multiChoiceDialogOkBtn click {
            listener.newFiltersSelected(multiChoiceAdapter.content.filter { it.checked }.map { it.text })
            dismiss()
        }
    }

    fun show(
        fm: FragmentManager?,
        fullList: List<String>,
        alreadySelectedList: List<String>,
        listener: FilterSelectedListener
    ) {
        val items = fullList.map { MultiChoiceItem(checked = alreadySelectedList.contains(it), text = it) }
        multiChoiceAdapter.setList(items)
        this.listener = listener
        super.show(fm)
    }
}