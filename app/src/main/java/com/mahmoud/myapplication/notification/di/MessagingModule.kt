package com.mahmoud.myapplication.notification.di

import com.mahmoud.cloudmessaging.contract.NotificationConfigurationsContract
import com.mahmoud.cloudmessaging.contract.NotificationTokenContract
import com.mahmoud.myapplication.notification.contracts.NotificationConfigurationsContractImpl
import com.mahmoud.myapplication.notification.contracts.NotificationTokenContractImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MessagingModule {

    @Singleton
    @Binds
    internal abstract fun provideNotificationConfigurationsContract(impl: NotificationConfigurationsContractImpl): NotificationConfigurationsContract

    @Singleton
    @Binds
    internal abstract fun provideNotificationTokenContract(impl: NotificationTokenContractImpl): NotificationTokenContract
}
