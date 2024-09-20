package com.swissborg.domain

import com.swissborg.domain.repository.BitfinexRepository
import com.swissborg.domain.repository.OnGetTickersCompleted
import com.swissborg.domain.model.TickerModel
import com.swissborg.domain.usecase.bitfinexRepository.GetTickersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.times

class GetTickersUseCaseTest {

    private lateinit var getTickersUseCase: GetTickersUseCase
    private val repository: BitfinexRepository = mock()
    private val onGetTickersCompleted: OnGetTickersCompleted = mock()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        getTickersUseCase = GetTickersUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should invoke repository getTickers every 5 seconds`() = runTest(testDispatcher) {
        // Ejecuta el caso de uso
        getTickersUseCase.invoke(onGetTickersCompleted)

        // Avanza el tiempo simulado
        advanceTimeBy(5000)

        // Verifica que el repositorio fue llamado una vez en los primeros 5 segundos
        verify(repository, times(1)).getTickers(any())

        // Avanza otros 5 segundos y verifica que se vuelve a llamar
        advanceTimeBy(5000)
        verify(repository, times(2)).getTickers(any())
    }
}
