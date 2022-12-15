package com.mahmoud.cloudmessaging.domain.usecase

import com.mahmoud.cloudmessaging.core.SuspendUseCase
import com.mahmoud.cloudmessaging.domain.repository.CMRepository
import javax.inject.Inject

class UpdateCMTokenUseCase @Inject constructor(private val CMRepository: CMRepository) :
    SuspendUseCase<String, Unit> {
    override suspend fun invoke(param: String) =
        CMRepository.updateCMToken(param)
}
