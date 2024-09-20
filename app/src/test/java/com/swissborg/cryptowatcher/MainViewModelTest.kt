package com.swissborg.cryptowatcher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.swissborg.cryptowatcher.ui.features.mainScreen.MainViewModel
import com.swissborg.domain.error.OutputError
import com.swissborg.domain.model.TickerModel
import com.swissborg.domain.repository.OnGetTickersCompleted
import com.swissborg.domain.usecase.bitfinexRepository.GetTickersUseCase
import com.swissborg.domain.usecase.networkRepository.ObserveNetworkStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule() // Para ejecutar LiveData en el hilo principal

    private val getTickersUseCase: GetTickersUseCase = mock()
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase = mock()
    private lateinit var mainViewModel: MainViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(getTickersUseCase, observeNetworkStatusUseCase)
    }

    @Test
    fun `should get tickers when initialized`() = runTest(testDispatcher) {
        // Verifica que se invoque el caso de uso al inicializar el ViewModel
        verify(getTickersUseCase).invoke(any())
    }

    @Test
    fun `should set tickers on success`() = runTest(testDispatcher) {
        val tickers = listOf(TickerModel("tBTCUSD", 100.0, 0.1, 101.0, 0.2, 1.0, 2.0, 102.0, 1000.0, 103.0, 99.0))

        mainViewModel.onGetTickersSuccess(tickers)

        assert(mainViewModel.tickers.value == tickers)
        assert(mainViewModel.error.value == null)
    }

    @Test
    fun `should set error on network failure`() = runTest(testDispatcher) {
        whenever(observeNetworkStatusUseCase()).thenReturn(flowOf(false))

        mainViewModel.onGetTickersError(OutputError.FetchDataError)

        assert(mainViewModel.error.value != null)
    }
}
