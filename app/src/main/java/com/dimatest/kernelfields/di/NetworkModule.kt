package com.dimatest.kernelfields.di

import com.dimatest.kernelfields.BuildConfig
import com.dimatest.kernelfields.network.FieldsService
import com.dimatest.kernelfields.utils.HEADER
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single { }
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(get() as HttpLoggingInterceptor)
            .addInterceptor(provideHeaderInterceptor())
            .build()
    }
    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    factory { provideFieldsService(get()) }
}

fun provideFieldsService(retrofit: Retrofit): FieldsService {
    return retrofit.create(FieldsService::class.java)
}

fun provideHeaderInterceptor() = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(HEADER, BuildConfig.DEV_ID)
                .build()
        )
    }
}