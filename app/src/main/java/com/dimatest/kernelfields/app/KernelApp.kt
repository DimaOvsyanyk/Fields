package com.dimatest.kernelfields.app

import android.app.Application
import com.dimatest.kernelfields.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KernelApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@KernelApp)
            modules(listOf(appModule, networkModule, databaseModule,
                repositoryModule, useCaseModule))
        }
    }
}