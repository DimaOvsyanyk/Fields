package com.dimatest.kernelfields.repositories.remote

import com.dimatest.kernelfields.network.entities.Field
import io.reactivex.Single

interface FieldsRemoteRepositoryInterface {

    fun getFieldsList(): Single<List<Field>>
}