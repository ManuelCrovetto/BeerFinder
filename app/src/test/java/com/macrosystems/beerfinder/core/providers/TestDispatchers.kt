package com.macrosystems.beerfinder.core.providers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatchers: DefaultDispatchers() {
    val testDispatcher = StandardTestDispatcher()
    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
}