package com.dimatest.kernelfields.di

import com.dimatest.kernelfields.usecases.FetchFieldsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module

val useCaseModule = module {
    factory { FetchFieldsUseCase(get(), get(), AndroidSchedulers.mainThread(), Schedulers.io(), get()) }
}