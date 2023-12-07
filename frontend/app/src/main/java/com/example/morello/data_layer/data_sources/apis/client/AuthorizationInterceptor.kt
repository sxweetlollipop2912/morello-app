package com.example.morello.data_layer.data_sources.apis.client

import okhttp3.Interceptor
import java.util.UUID

object AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val requestWithHeader = chain.request()
            .newBuilder()
            .header(
                "Authorization", UUID.randomUUID().toString()
            ).build()
        return chain.proceed(requestWithHeader)
    }
}
