package com.swissborg.cryptowatcher.ui.features.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swissborg.cryptowatcher.error.ErrorManagerImpl
import com.swissborg.domain.error.ErrorManager
import com.swissborg.domain.error.OutputError
import com.swissborg.domain.model.TickerModel
import com.swissborg.domain.repository.OnGetTickersCompleted
import com.swissborg.domain.usecase.bitfinexRepository.GetTickersUseCase
import com.swissborg.domain.usecase.networkRepository.ObserveNetworkStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTickersUseCase: GetTickersUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
    private val errorManager: ErrorManager
) : ViewModel(), OnGetTickersCompleted {

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
                _isConnected.value = hasConnection
                if (!hasConnection) setError(errorManager.convert(OutputError.NoInternetError)) else setError(null)
            }
        }
    }

    private fun getTickers() {
        viewModelScope.launch {
            getTickersUseCase(this@MainViewModel)

        }
    }

    private fun setTickers(tickers: List<TickerModel>) {
        //Log.d("MainViewModel", "Setting tickers: $tickers")
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