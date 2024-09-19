package com.swissborg.domain.error

interface ErrorManager {
    fun convert(error: OutputError): String
}