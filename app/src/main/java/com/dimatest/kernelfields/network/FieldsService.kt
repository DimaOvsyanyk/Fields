package com.dimatest.kernelfields.network

import com.dimatest.kernelfields.network.entities.Field
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FieldsService {

    @GET("api/GetCornFieldsByDevice")
    fun getFieldsList(@Query("dev_ID") devId: String): Single<List<Field>>
}