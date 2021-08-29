package com.macrosystems.beerfinder.data.network.response

import java.lang.Exception

sealed class Result <out T> {
    data class Error <T> (val message: Exception?): Result<T>()
    data class Success <T> (val data: T): Result<T>()
}