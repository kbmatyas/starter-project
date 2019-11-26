package com.possible.demo.data

sealed class ServerError : Throwable() {

    object GeneralError : ServerError()
    object NetworkError : ServerError()
}