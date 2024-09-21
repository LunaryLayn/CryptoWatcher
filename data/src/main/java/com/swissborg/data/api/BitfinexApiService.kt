/*
 * © Hugo 2024 for SwissBorg technical challenge
 */

package com.swissborg.data.api

import com.swissborg.data.constants.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface BitfinexApiService {

    @GET("v2/tickers")
    suspend fun getTickers(@Query("symbols") symbols: String = AppConstants.SYMBOL_LIST): List<List<Any>>

}
