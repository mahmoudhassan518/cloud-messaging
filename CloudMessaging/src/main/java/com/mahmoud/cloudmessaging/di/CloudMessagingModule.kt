package com.mahmoud.cloudmessaging.di

import com.mahmoud.cloudmessaging.data.repository.CMRepositoryImpl
import com.mahmoud.cloudmessaging.domain.repository.CMRepository
import com.mahmoud.cloudmessaging.domain.usecase.UpdateCMTokenUseCase
import com.mahmoud.cloudmessaging.presentation.viewmodel.CMServiceController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CloudMessagingModule {
    @Singleton
    @Binds
    abstract fun bindCMRepository(impl: CMRepositoryImpl): CMRepository
}

@Module
@InstallIn(ServiceComponent::class)
class NotificationPresentationModule {

    @Provides
    @ServiceScoped
    fun provideCMServiceController(
        updateCMTokenUseCase: UpdateCMTokenUseCase
    ): CMServiceController = CMServiceController(updateCMTokenUseCase)
}
