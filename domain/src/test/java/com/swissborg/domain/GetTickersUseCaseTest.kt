package com.swissborg.domain

import com.swissborg.domain.repository.BitfinexRepository
import com.swissborg.domain.repository.NetworkRepository
import com.swissborg.domain.repository.OnGetTickersCompleted
import com.swissborg.domain.usecase.bitfinexRepository.GetTickersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.*

class GetTickersUseCaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var repository: BitfinexRepository

    @Mock
    lateinit var networkRepository: NetworkRepository

    @Mock
    lateinit var onGetTickersCompleted: OnGetTickersCompleted

    private lateinit var useCase: GetTickersUseCase
    private lateinit var networkStatusFlow: MutableStateFlow<Boolean>

    @Before
    fun setUp() {
        networkStatusFlow = MutableStateFlow(false)
        whenever(networkRepository.observeNetworkStatus()).thenReturn(networkStatusFlow)
        useCase = GetTickersUseCase(repository, networkRepository)
    }

    // Verifies that when connected, the repository's getTickers is called repeatedly at intervals.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when connected, repository getTickers is called repeatedly`() = runTest {
        networkStatusFlow.value = true

        val job = launch { useCase.invoke(onGetTickersCompleted) }

        advanceTimeBy(5000)
        verify(repository, times(1)).getTickers(onGetTickersCompleted)

        advanceTimeBy(5000)
        verify(repository, times(2)).getTickers(onGetTickersCompleted)

        job.cancel()
    }

    // Verifies that when not connected, the repository's getTickers method is never called.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when not connected, repository getTickers is not called`() = runTest {
        networkStatusFlow.value = false

        val job = launch { useCase.invoke(onGetTickersCompleted) }

        advanceUntilIdle()

        verify(repository, never()).getTickers(onGetTickersCompleted)

        job.cancel()
    }

    // Verifies that if the repository throws an exception, it is caught and handled.
    @Test
    fun `when repository throws exception, it is caught and logged`() = runTest {
        networkStatusFlow.value = true

        whenever(repository.getTickers(onGetTickersCompleted)).thenThrow(RuntimeException("Test Exception"))

        val job = launch { useCase.invoke(onGetTickersCompleted) }

        advanceTimeBy(5000)

        verify(repository).getTickers(onGetTickersCompleted)

        job.cancel()
    }

    // Verifies that the repository is called every 5 seconds while connected.
    @Test
    fun `repository is called every 5 seconds when connected`() = runTest {
        networkStatusFlow.value = true

        val job = launch { useCase.invoke(onGetTickersCompleted) }

        advanceTimeBy(5000)
        verify(repository, times(1)).getTickers(onGetTickersCompleted)

        advanceTimeBy(10000)
        verify(repository, times(3)).getTickers(onGetTickersCompleted)

        job.cancel()
    }

    // Verifies that after the connection is lost, the repository is not called again.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `repository is not called again after connection is lost`() = runTest {
        networkStatusFlow.value = true

        val job = launch { useCase.invoke(onGetTickersCompleted) }

        advanceTimeBy(4000)
        verify(repository, times(1)).getTickers(onGetTickersCompleted)

        networkStatusFlow.value = false

        advanceTimeBy(10000)

        verify(repository, times(1)).getTickers(onGetTickersCompleted)

        job.cancel()
    }

}
