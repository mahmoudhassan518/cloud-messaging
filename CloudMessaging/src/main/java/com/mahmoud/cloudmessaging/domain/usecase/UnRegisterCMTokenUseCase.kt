package com.mahmoud.cloudmessaging.domain.usecase

import com.mahmoud.cloudmessaging.core.SuspendUseCase
import com.mahmoud.cloudmessaging.domain.repository.CMRepository
import javax.inject.Inject

class UnRegisterCMTokenUseCase @Inject constructor(
    private val CMRepository: CMRepository
) : SuspendUseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit) =
        CMRepository.unRegisterCMToken()
}
