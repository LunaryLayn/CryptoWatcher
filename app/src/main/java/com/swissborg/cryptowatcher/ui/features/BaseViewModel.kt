package com.swissborg.cryptowatcher.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swissborg.cryptowatcher.error.ErrorManagerImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    protected lateinit var errorManager: ErrorManagerImpl

}