package com.swissborg.domain.repository

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun observeNetworkStatus(): Flow<Boolean>
}