package com.swissborg.data.repository

import android.util.Log
import com.swissborg.data.api.BitfinexApiService
import com.swissborg.data.mapper.toDto
import com.swissborg.data.model.TickerDataModel
import com.swissborg.domain.error.OutputError
import com.swissborg.domain.repository.BitfinexRepository
import com.swissborg.domain.repository.NetworkRepository
import com.swissborg.domain.repository.OnGetTickersCompleted

class BitfinexRepositoryImpl(
    private val apiService: BitfinexApiService
) : BitfinexRepository {
    override suspend fun getTickers(onGetTickersCompleted: OnGetTickersCompleted) {
        try {
            val response = apiService.getTickers()
            Log.d("BitfinexRepositoryImpl", "Response: $response")
            val tickersList = response.map { item ->
                TickerDataModel(
                    symbol = item[0] as String,
                    bid = item[1] as Double,
                    bidSize = item[2] as Double,
                    ask = item[3] as Double,
                    askSize = item[4] as Double,
                    dailyChange = item[5] as Double,
                    dailyChangePerc = item[6] as Double,
                    lastPrice = item[7] as Double,
                    volume = item[8] as Double,
                    high = item[9] as Double,
                    low = item[10] as Double
                )
            }

            val tickersModelList = tickersList.map { it.toDto() }

            onGetTickersCompleted.onGetTickersSuccess(tickersModelList)

        } catch (e: Exception) {
            onGetTickersCompleted.onGetTickersError(OutputError.FetchDataError)
        }
    }

}
