package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedBalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedGroupApi
import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedMemberApi
import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedUserApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ApiModule {
    @Binds
    @ViewModelScoped
    abstract fun bindUserApi(userApiImpl: MockedUserApi): UserApi

    @Binds
    @ViewModelScoped
    abstract fun bindBalanceEntryApi(balanceEntryApiImpl: MockedBalanceEntryApi): BalanceEntryApi

    @Binds
    @ViewModelScoped
    abstract fun bindGroupApi(groupApiImpl: MockedGroupApi): GroupApi

    @Binds
    @ViewModelScoped
    abstract fun bindMemberApi(memberApiImpl: MockedMemberApi): MemberApi
}