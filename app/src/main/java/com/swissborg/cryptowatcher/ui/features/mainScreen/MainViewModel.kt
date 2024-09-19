package com.swissborg.cryptowatcher.ui.features.mainScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.swissborg.cryptowatcher.ui.features.BaseViewModel
import com.swissborg.domain.error.OutputError
import com.swissborg.domain.model.TickerModel
import com.swissborg.domain.repository.OnGetTickersCompleted
import com.swissborg.domain.usecase.bitfinexRepository.GetTickersUseCase
import com.swissborg.domain.usecase.networkRepository.ObserveNetworkStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTickersUseCase: GetTickersUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase
) : BaseViewModel(), OnGetTickersCompleted {

    private val _tickers = MutableStateFlow<List<TickerModel>>(emptyList())
    val tickers = _tickers.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    init {
        observeNetworkStatus()
        getTickers()
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            observeNetworkStatusUseCase().collect { hasConnection ->
                Log.d("MainViewModel", "Has connection: $hasConnection")
                _isConnected.value = hasConnection
            }
        }
    }

    private fun getTickers() {
        viewModelScope.launch {
            isConnected.collect { isConnected ->
                if (isConnected) {
                    Log.d("MainViewModel", "Starting to retrieve tickers periodically")
                    getTickersUseCase(this@MainViewModel)  // Llama al use case
                } else {
                    Log.d("MainViewModel", "No internet connection, stopping ticker retrieval")
                    getTickersUseCase.stop()  // Detenemos el ciclo en el UseCase
                }
            }
        }
    }

    private fun setTickers(tickers: List<TickerModel>) {
        Log.d("MainViewModel", "Setting tickers: $tickers")
        _tickers.value = tickers
    }

    private fun setError(error: String?) {
        _error.value = error
    }


    override fun onGetTickersSuccess(tickers: List<TickerModel>) {
        setError(null)
        setTickers(tickers)

    }

    override fun onGetTickersError(error: OutputError) {
        if (!isConnected.value) {
            setError(errorManager.convert(error))
        }
    }
}