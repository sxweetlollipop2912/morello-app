package com.example.morello.data_layer.data_sources.apis.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://morello-api.herokuapp.com/api/v1/"
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(RequestInterceptor)
        .build()

    fun getClient(): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}