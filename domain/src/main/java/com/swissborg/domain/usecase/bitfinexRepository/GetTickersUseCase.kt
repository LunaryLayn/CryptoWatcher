package com.swissborg.domain.usecase.bitfinexRepository

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
            Log.d("GetTickersUseCase", "Cambio de conexion: $isConnected")
            if (isConnected) {
                while (true) {
                    try {
                        Log.d("GetTickersUseCase", "Vueltesita")
                        repository.getTickers(onGetTickersCompleted)
                    } catch (e: Exception) {
                        Log.d("GetTickersUseCase", "Error: $e")
                    }
                    delay(5000)
                }
            } else {
                Log.d("GetTickersUseCase", "No hay conexion")
            }
        }
    }
}