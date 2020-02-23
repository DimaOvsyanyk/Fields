package com.dimatest.kernelfields.di

import androidx.room.Room
import com.dimatest.kernelfields.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, "kernel_database.db"
        )
            .build()
    }
}