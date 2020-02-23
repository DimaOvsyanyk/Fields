package com.dimatest.kernelfields.ui.fields

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.dimatest.kernelfields.common.BaseViewModel
import com.dimatest.kernelfields.database.entities.FieldDO
import com.dimatest.kernelfields.repositories.local.FieldsLocalRepositoryInterface
import com.dimatest.kernelfields.usecases.FetchFieldsUseCase
import com.dimatest.kernelfields.utils.addToDisposable

class FieldsViewModel(
    private val fetchFieldsUseCase: FetchFieldsUseCase,
    fieldsLocalRepository: FieldsLocalRepositoryInterface
) : BaseViewModel() {

    val fieldsList = fieldsLocalRepository.getAll()

    val fieldNoDescrFilterList = ObservableField<List<String>>()
    val fieldNoFilterList = ObservableField<List<String>>()
    val cornTypeFilterList = ObservableField<List<String>>()

    val filtersVis = ObservableBoolean(false)

    val fullFieldNoDescrFilterList = mutableListOf<String>()
    val fullFieldNoFilterList = mutableListOf<String>()
    val fullCornTypeFilterList = mutableListOf<String>()

    init {
        loading.addSource(fetchFieldsUseCase.isLoading) { loading.value = it }
        fetchField()
    }

    private fun fetchField() {
        fetchFieldsUseCase.execute(Unit).addToDisposable(disposable)
    }

    fun changeFilterVis() {
        filtersVis.set(!filtersVis.get())
    }

    fun saveFilterLists(fieldsList: List<FieldDO>) {
        fullFieldNoDescrFilterList.clear()
        fullFieldNoFilterList.clear()
        fullCornTypeFilterList.clear()
        fullFieldNoDescrFilterList.addAll(fieldsList.map { it.fieldNoDescr ?: "" }.filter { it.isNotBlank() }.distinct())
        fullFieldNoFilterList.addAll(fieldsList.map { it.fieldNo ?: "" }.filter { it.isNotBlank() }.distinct())
        fullCornTypeFilterList.addAll(fieldsList.map { it.cornType ?: "" }.filter { it.isNotBlank() }.distinct())
    }

    fun clearFilters() {
        fieldNoDescrFilterList.set(emptyList())
        fieldNoFilterList.set(emptyList())
        cornTypeFilterList.set(emptyList())
        changeFilterVis()
    }
}