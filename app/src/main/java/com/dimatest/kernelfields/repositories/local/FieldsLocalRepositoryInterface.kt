package com.dimatest.kernelfields.repositories.local

import androidx.lifecycle.LiveData
import com.dimatest.kernelfields.database.entities.FieldDO
import io.reactivex.Completable

interface FieldsLocalRepositoryInterface {

    fun insertAll(fieldList: List<FieldDO>) : Completable
    fun deleteAll() : Completable
    fun getAll(): LiveData<List<FieldDO>>
    fun insertNewFieldList(fieldList: List<FieldDO>) : Completable
}