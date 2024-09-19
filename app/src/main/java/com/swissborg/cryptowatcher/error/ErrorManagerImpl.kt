package com.swissborg.cryptowatcher.error

import android.content.Context
import com.swissborg.cryptowatcher.R
import com.swissborg.domain.error.ErrorManager
import com.swissborg.domain.error.OutputError
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorManagerImpl @Inject constructor(@ApplicationContext private val context: Context) : ErrorManager {
    override fun convert(error: OutputError): String {
        return when (error) {
            OutputError.FetchDataError -> context.getString(R.string.error_fetch_data)
            OutputError.NoInternetError -> context.getString(R.string.error_no_internet)
            else -> context.getString(R.string.default_error)
        }
    }
}