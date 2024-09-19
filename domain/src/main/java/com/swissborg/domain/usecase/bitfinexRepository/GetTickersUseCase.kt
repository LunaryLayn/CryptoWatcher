package com.swissborg.domain.usecase.bitfinexRepository

import com.swissborg.domain.repository.BitfinexRepository
import com.swissborg.domain.repository.OnGetTickersCompleted
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetTickersUseCase @Inject constructor(
    private val repository: BitfinexRepository
) {
    suspend operator fun invoke(onGetTickersCompleted: OnGetTickersCompleted) {
        while (true) {
            repository.getTickers(onGetTickersCompleted)
            delay(5000)
        }
    }
}