package com.possible.demo.data

sealed class DataResult<out Data, out E : Throwable> {
    data class Success<Data>(val value: Data) : DataResult<Data, Nothing>()
    data class Error<E : Throwable>(val throwable: E) : DataResult<Nothing, E>()
}