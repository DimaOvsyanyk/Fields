package com.dimatest.kernelfields.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.dimatest.kernelfields.database.entities.FieldDO
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface FieldDao {

    @Insert(onConflict = REPLACE)
    fun insertAll(fieldList: List<FieldDO>) : Completable

    @Query("DELETE FROM fields")
    fun deleteAll() : Completable

    @Query("SELECT * FROM fields")
    fun getAll(): LiveData<List<FieldDO>>
}