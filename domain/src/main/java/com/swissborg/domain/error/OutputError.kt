package com.swissborg.domain.error

sealed class OutputError : Throwable() {
    object DefaultError : OutputError()
    object FetchDataError : OutputError()

    //This error are only here for showcasing purposes, as they won't be used in the real app
    object NoInternetError : OutputError()
}