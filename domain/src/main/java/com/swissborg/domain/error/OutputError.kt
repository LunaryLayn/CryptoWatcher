package com.swissborg.domain.error

sealed class OutputError : Throwable() {
    object FetchDataError : OutputError()
    object NoInternetError : OutputError()

    object DefaultError : OutputError()
}