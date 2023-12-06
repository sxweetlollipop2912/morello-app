package com.example.morello.data_layer.data_sources

import okhttp3.Interceptor

object RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        println("Request: ${request.url()}")
        return chain.proceed(request)
    }
}