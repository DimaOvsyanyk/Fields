package com.dimatest.kernelfields.di

import com.dimatest.kernelfields.repositories.local.FieldsLocalRepository
import com.dimatest.kernelfields.repositories.local.FieldsLocalRepositoryInterface
import com.dimatest.kernelfields.repositories.remote.FieldsRemoteRepository
import com.dimatest.kernelfields.repositories.remote.FieldsRemoteRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {
    factory { FieldsRemoteRepository(get()) as FieldsRemoteRepositoryInterface }
    factory { FieldsLocalRepository(get()) as FieldsLocalRepositoryInterface }
}