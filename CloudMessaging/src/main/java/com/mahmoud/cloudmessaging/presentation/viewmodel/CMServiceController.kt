package com.mahmoud.cloudmessaging.presentation.viewmodel

import com.mahmoud.cloudmessaging.core.BaseServiceController
import com.mahmoud.cloudmessaging.domain.usecase.UpdateCMTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

class CMServiceController @Inject constructor(
    private val updateCMTokenUseCase: UpdateCMTokenUseCase
) : BaseServiceController(Dispatchers.IO) {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        initCoroutineScope(serviceScope)
    }

    fun updateFcmToken(token: String) = launchBlock {
        updateCMTokenUseCase(token)
    }

    fun onDestroy() {
        serviceScope.cancel()
    }
}
