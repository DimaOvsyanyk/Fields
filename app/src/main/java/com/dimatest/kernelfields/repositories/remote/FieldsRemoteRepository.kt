package com.dimatest.kernelfields.repositories.remote

import com.dimatest.kernelfields.BuildConfig
import com.dimatest.kernelfields.network.FieldsService
import com.dimatest.kernelfields.network.entities.Field
import io.reactivex.Single

class FieldsRemoteRepository(
    private val fieldsService: FieldsService
) : FieldsRemoteRepositoryInterface {

    override fun getFieldsList(): Single<List<Field>> {
        return fieldsService.getFieldsList(BuildConfig.DEV_ID)
    }
}