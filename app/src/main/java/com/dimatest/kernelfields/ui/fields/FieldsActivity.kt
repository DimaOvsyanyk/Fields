package com.dimatest.kernelfields.ui.fields

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimatest.kernelfields.ui.map.MapsActivity
import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.common.BaseActivity
import com.dimatest.kernelfields.database.entities.FieldDO
import com.dimatest.kernelfields.databinding.ActivityFieldsBinding
import com.dimatest.kernelfields.dialogs.multiChoiceDialog.MultiChoiceDialog
import com.dimatest.kernelfields.utils.click
import com.dimatest.kernelfields.utils.getTxt
import org.koin.androidx.viewmodel.ext.android.viewModel

class FieldsActivity : BaseActivity<ActivityFieldsBinding>(), FieldsAdapter.FieldSelected {

    override fun getLayoutRes() = R.layout.activity_fields

    private val viewModel: FieldsViewModel by viewModel()

    private val fieldsAdapter: FieldsAdapter by lazy {
        FieldsAdapter(this)
    }

    private val multiChoiceDialog: MultiChoiceDialog by lazy {
        MultiChoiceDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setupList()
        observeModel()
        setupListeners()
    }

    private fun setupList() {
        binding.fieldsRv.apply {
            layoutManager = LinearLayoutManager(this@FieldsActivity)
            adapter = fieldsAdapter
        }
    }

    private fun observeModel() {
        viewModel.fieldsList.observe(this, Observer {
            fieldsAdapter.setData(it)
            viewModel.saveFilterLists(it)
        })
        viewModel.loading.observe(this, Observer {
            showLoader(it)
        })
    }

    private fun setupListeners() {
        binding.searchEt.doAfterTextChanged {
            fieldsAdapter.performSearch(binding.searchEt.getTxt())
        }
        binding.fieldNoDescrFilter click {
            multiChoiceDialog.show(
                supportFragmentManager,
                viewModel.fullFieldNoDescrFilterList,
                viewModel.fieldNoDescrFilterList.get() ?: emptyList(),
                object : MultiChoiceDialog.FilterSelectedListener {
                    override fun newFiltersSelected(filterList: List<String>) {
                        viewModel.fieldNoDescrFilterList.set(filterList)
                    }
                }
            )
        }
        binding.fieldNoFilter click {
            multiChoiceDialog.show(
                supportFragmentManager,
                viewModel.fullFieldNoFilterList,
                viewModel.fieldNoFilterList.get() ?: emptyList(),
                object : MultiChoiceDialog.FilterSelectedListener {
                    override fun newFiltersSelected(filterList: List<String>) {
                        viewModel.fieldNoFilterList.set(filterList)
                    }
                }
            )
        }
        binding.cornTypeFilter click {
            multiChoiceDialog.show(
                supportFragmentManager,
                viewModel.fullCornTypeFilterList,
                viewModel.cornTypeFilterList.get() ?: emptyList(),
                object : MultiChoiceDialog.FilterSelectedListener {
                    override fun newFiltersSelected(filterList: List<String>) {
                        viewModel.cornTypeFilterList.set(filterList)
                    }
                }
            )
        }
        binding.filterClearBtn click {
            viewModel.clearFilters()
            search()
        }
        binding.filterOkBtn click {
            search()
            viewModel.changeFilterVis()
        }
    }

    private fun search() {
        fieldsAdapter.apply {
            fieldNoDescrFilterList = viewModel.fieldNoDescrFilterList.get() ?: emptyList()
            fieldNoFilterList = viewModel.fieldNoFilterList.get() ?: emptyList()
            cornTypeFilterList = viewModel.cornTypeFilterList.get() ?: emptyList()
            performSearch(binding.searchEt.getTxt())
        }
    }

    override fun selectField(field: FieldDO) {
        val intent = Intent(this, MapsActivity::class.java).apply {
            putExtra(FIELD_EXTRA_KEY, field)
        }
        startActivity(intent)
    }

    companion object {
        const val FIELD_EXTRA_KEY = "field"
    }
}
