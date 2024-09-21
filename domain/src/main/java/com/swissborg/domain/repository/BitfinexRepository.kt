/*
 * © Hugo 2024 for SwissBorg technical challenge
 */

package com.swissborg.domain.repository

import com.swissborg.domain.error.OutputError
import com.swissborg.domain.model.TickerModel

interface BitfinexRepository {
    suspend fun getTickers(onGetTickersCompleted: OnGetTickersCompleted)
}

interface OnGetTickersCompleted {
    fun onGetTickersSuccess(tickers: List<TickerModel>)
    fun onGetTickersError(error: OutputError)
}