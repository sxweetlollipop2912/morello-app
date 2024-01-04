package com.example.morello.data_layer.apis.client

import okhttp3.Interceptor
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        println("Request: ${request.url()}")
        return chain.proceed(request)
    }
}