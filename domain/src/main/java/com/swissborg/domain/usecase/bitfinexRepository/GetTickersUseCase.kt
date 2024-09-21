/*
 * © Hugo 2024 for SwissBorg technical challenge
 */

package com.swissborg.domain.usecase.bitfinexRepository

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

    /*This implementation is used to check if the device is
    connected to the internet, so the app won't try to fetch data if there is no
    internet connection to save resources*/


    private val isConnected = networkRepository.observeNetworkStatus()

    suspend operator fun invoke(onGetTickersCompleted: OnGetTickersCompleted) {
        isConnected.collectLatest { isConnected ->

            if (isConnected) {
                while (isConnected) {
                    try {
                        repository.getTickers(onGetTickersCompleted)
                    } catch (e: Exception) {
                        onGetTickersCompleted.onGetTickersError(OutputError.DefaultError)
                    }
                    delay(5000)
                }
            } else {
                //Log.d("GetTickersUseCase", "No Internet") Uncomment this line to see the log
            }
        }
    }
}