package com.example.morello.data_layer.apis.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.TimeZone
import javax.inject.Qualifier
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthRetrofitClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://192.168.102.3:8000/api/"
    private val objectMapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)  // add this line
        .setTimeZone(TimeZone.getTimeZone("UTC"))

    @AuthOkHttpClient
    @Provides
    fun provideAuthOkHttpClient(
        authInterceptor: AuthInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @NoAuthOkHttpClient
    @Provides
    fun provideNoAuthOkHttpClient(
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
    }

    @AuthRetrofitClient
    @Provides
    fun provideAuthRetrofitClient(
        @AuthOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }

    @NoAuthRetrofitClient
    @Provides
    fun provideNoAuthRetrofitClient(
        @NoAuthOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }
}