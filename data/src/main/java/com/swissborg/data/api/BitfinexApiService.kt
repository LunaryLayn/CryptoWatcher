package com.swissborg.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface BitfinexApiService {

    @GET("v2/tickers")
    suspend fun getTickers(@Query("symbols") symbols: String = "ALL"): List<List<Any>>

}
