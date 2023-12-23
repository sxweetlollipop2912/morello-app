package com.example.morello.data_layer.data_sources.apis.client

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Qualifier

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
    private const val BASE_URL = "https://morello-api.herokuapp.com/api/v1/"

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
            .addConverterFactory(JacksonConverterFactory.create())
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
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}