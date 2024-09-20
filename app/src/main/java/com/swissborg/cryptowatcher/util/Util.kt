package com.swissborg.cryptowatcher.util

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object Util {
    fun Double.formatNumber(): String {
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        val decimalFormat = DecimalFormat("#,##0.00", symbols)

        return if (this % 1.0 == 0.0) {
            decimalFormat.applyPattern("#,##0")
            decimalFormat.format(this)
        } else {
            decimalFormat.format(this)
        }
    }

    fun Modifier.labeledBackground(color: Color) = this.clip(RoundedCornerShape(24.dp)).background(color)
}
