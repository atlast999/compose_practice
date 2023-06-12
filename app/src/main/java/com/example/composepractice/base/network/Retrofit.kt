package com.example.composepractice.base.network

import com.example.composepractice.base.serialize.GsonSerializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun buildRetrofit(
    baseUrl: String,
): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(
        OkHttpClient.Builder().apply {
            addInterceptor(
                interceptor = createLoggingInterceptor()
            )
        }.build()
    )
    .addConverterFactory(
        GsonConverterFactory.create(
            GsonSerializer.provideGson()
        )
    )
    .build()


private fun createLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}