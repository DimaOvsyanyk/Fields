package com.dimatest.kernelfields.repositories.local

import androidx.lifecycle.LiveData
import com.dimatest.kernelfields.database.AppDatabase
import com.dimatest.kernelfields.database.entities.FieldDO
import io.reactivex.Completable

class FieldsLocalRepository(db: AppDatabase) : FieldsLocalRepositoryInterface {

    private val fieldDao = db.fieldDao()

    override fun insertAll(fieldList: List<FieldDO>): Completable {
        return fieldDao.insertAll(fieldList)
    }

    override fun deleteAll(): Completable {
        return fieldDao.deleteAll()
    }

    override fun getAll(): LiveData<List<FieldDO>> {
        return fieldDao.getAll()
    }

    override fun insertNewFieldList(fieldList: List<FieldDO>): Completable {
        return fieldDao.deleteAll()
            .andThen(insertAll(fieldList))
    }
}