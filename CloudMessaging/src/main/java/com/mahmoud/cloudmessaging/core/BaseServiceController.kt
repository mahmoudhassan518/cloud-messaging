package com.mahmoud.cloudmessaging.core

import kotlinx.coroutines.*

abstract class BaseServiceController(
    private val dispatcher: CoroutineDispatcher
) {

    private lateinit var scope: CoroutineScope

    fun initCoroutineScope(scope: CoroutineScope) {
        this.scope = scope
    }

    fun launchBlock(
        onStart: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        onStart?.invoke()
        scope.launch(dispatcher + coroutineExceptionHandler(onError)) {
            block.invoke(this)
        }
    }

    private fun coroutineExceptionHandler(onError: ((Throwable) -> Unit)?) =
        CoroutineExceptionHandler { _, throwable ->
            onError?.invoke(throwable)
        }
}
