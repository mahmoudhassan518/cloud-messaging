package com.mahmoud.cloudmessaging.core

interface SuspendUseCase<PARAM, TYPE> {
    suspend operator fun invoke(param: PARAM): TYPE
}
