package com.swissborg.cryptowatcher.ui.features

import androidx.lifecycle.ViewModel
import com.swissborg.cryptowatcher.error.ErrorManagerImpl
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    protected lateinit var errorManager: ErrorManagerImpl

}