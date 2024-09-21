/*
 * © Hugo 2024 for SwissBorg technical challenge
 */

package com.swissborg.domain.usecase.networkRepository

import com.swissborg.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNetworkStatusUseCase @Inject constructor(private val repository: NetworkRepository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.observeNetworkStatus()
    }
}