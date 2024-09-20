package com.swissborg.domain.usecase.bitfinexRepository

import android.icu.util.Output
import android.util.Log
import com.swissborg.domain.error.OutputError
import com.swissborg.domain.repository.BitfinexRepository
import com.swissborg.domain.repository.NetworkRepository
import com.swissborg.domain.repository.OnGetTickersCompleted
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetTickersUseCase @Inject constructor(
    private val repository: BitfinexRepository,
    private val networkRepository: NetworkRepository
) {

    private val isConnected = networkRepository.observeNetworkStatus()

    suspend operator fun invoke(onGetTickersCompleted: OnGetTickersCompleted) {
        isConnected.collectLatest { isConnected ->

            if (isConnected) {
                while (isConnected) {
                    try {
                        //Log.d("GetTickersUseCase", "Obteniendo tickers")
                        repository.getTickers(onGetTickersCompleted)
                    } catch (e: Exception) {
                        //Log.d("GetTickersUseCase", "Error: $e")
                        onGetTickersCompleted.onGetTickersError(OutputError.DefaultError)
                    }
                    delay(5000)
                }
            } else {
                //Log.d("GetTickersUseCase", "No hay conexion")
            }
        }
    }
}