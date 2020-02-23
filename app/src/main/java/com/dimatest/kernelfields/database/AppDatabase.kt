package com.dimatest.kernelfields.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dimatest.kernelfields.database.entities.FieldDO

@Database(entities = [FieldDO::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fieldDao(): FieldDao
}