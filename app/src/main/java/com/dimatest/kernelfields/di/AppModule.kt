package com.dimatest.kernelfields.di

import com.dimatest.kernelfields.ui.fields.FieldsViewModel
import com.dimatest.kernelfields.utils.ErrorHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ErrorHandler(get()) }
    viewModel { FieldsViewModel(get(), get()) }
}