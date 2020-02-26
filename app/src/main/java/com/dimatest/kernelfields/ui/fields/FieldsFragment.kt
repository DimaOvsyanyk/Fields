package com.dimatest.kernelfields.ui.fields

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.common.BaseFragment
import com.dimatest.kernelfields.database.entities.FieldDO
import com.dimatest.kernelfields.databinding.FragmentFieldsBinding
import com.dimatest.kernelfields.dialogs.multiChoiceDialog.MultiChoiceDialog
import com.dimatest.kernelfields.utils.click
import com.dimatest.kernelfields.utils.getTxt
import com.dimatest.kernelfields.utils.observeNotNull
import kotlinx.android.synthetic.main.fragment_fields.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FieldsFragment : BaseFragment<FieldsViewModel, FragmentFieldsBinding>(), FieldsAdapter.FieldSelected {

    override fun getLayoutRes() = R.layout.fragment_fields

    override val viewModel: FieldsViewModel by viewModel()

    private val fieldsAdapter: FieldsAdapter by lazy {
        FieldsAdapter(this)
    }

    private val multiChoiceDialog: MultiChoiceDialog by lazy {
        MultiChoiceDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeModel()
        setupListeners()
    }

    private fun setupList() {
        fieldsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fieldsAdapter
        }
    }

    private fun observeModel() {
        viewModel.fieldsList.observeNotNull(viewLifecycleOwner) {
            fieldsAdapter.setData(it)
            viewModel.saveFilterLists(it)
        }
    }

    private fun setupListeners() {
        searchEt.doAfterTextChanged {
            fieldsAdapter.performSearch(searchEt.getTxt())
        }
        fieldNoDescrFilter click {
            multiChoiceDialog.show(
                childFragmentManager,
                viewModel.fullFieldNoDescrFilterList,
                viewModel.fieldNoDescrFilterList.get() ?: emptyList(),
                object : MultiChoiceDialog.FilterSelectedListener {
                    override fun newFiltersSelected(filterList: List<String>) {
                        viewModel.fieldNoDescrFilterList.set(filterList)
                    }
                }
            )
        }
        fieldNoFilter click {
            multiChoiceDialog.show(
                childFragmentManager,
                viewModel.fullFieldNoFilterList,
                viewModel.fieldNoFilterList.get() ?: emptyList(),
                object : MultiChoiceDialog.FilterSelectedListener {
                    override fun newFiltersSelected(filterList: List<String>) {
                        viewModel.fieldNoFilterList.set(filterList)
                    }
                }
            )
        }
        cornTypeFilter click {
            multiChoiceDialog.show(
                childFragmentManager,
                viewModel.fullCornTypeFilterList,
                viewModel.cornTypeFilterList.get() ?: emptyList(),
                object : MultiChoiceDialog.FilterSelectedListener {
                    override fun newFiltersSelected(filterList: List<String>) {
                        viewModel.cornTypeFilterList.set(filterList)
                    }
                }
            )
        }
        filterClearBtn click {
            viewModel.clearFilters()
            search()
        }
        filterOkBtn click {
            search()
            viewModel.changeFilterVis()
        }
    }

    private fun search() {
        fieldsAdapter.apply {
            fieldNoDescrFilterList = viewModel.fieldNoDescrFilterList.get() ?: emptyList()
            fieldNoFilterList = viewModel.fieldNoFilterList.get() ?: emptyList()
            cornTypeFilterList = viewModel.cornTypeFilterList.get() ?: emptyList()
            performSearch(searchEt.getTxt())
        }
    }

    override fun selectField(field: FieldDO) {
        navigate(FieldsFragmentDirections.actionFieldsFragmentToMapsFragment(field))
    }
}
