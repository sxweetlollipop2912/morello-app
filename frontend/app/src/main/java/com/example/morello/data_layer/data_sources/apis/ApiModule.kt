package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.apis.client.AuthRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ApiModule {
    @Provides
    @ViewModelScoped
    fun bindUserApi(@AuthRetrofitClient retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @ViewModelScoped
    fun bindBalanceEntryApi(@AuthRetrofitClient retrofit: Retrofit): BalanceEntryApi =
        retrofit.create(BalanceEntryApi::class.java)

    @Provides
    @ViewModelScoped
    fun bindGroupApi(@AuthRetrofitClient retrofit: Retrofit): GroupApi =
        retrofit.create(GroupApi::class.java)

    @Provides
    @ViewModelScoped
    fun bindMemberApi(@AuthRetrofitClient retrofit: Retrofit): MemberApi =
        retrofit.create(MemberApi::class.java)
}