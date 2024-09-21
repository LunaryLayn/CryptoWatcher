/*
 * © Hugo 2024 for SwissBorg technical challenge
 */

package com.swissborg.domain.error

interface ErrorManager {
    fun convert(error: OutputError): String
}