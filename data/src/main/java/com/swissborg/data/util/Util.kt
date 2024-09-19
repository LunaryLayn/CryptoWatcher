package com.swissborg.data.util

object DataUtil {
    fun formatNumber(number: Double): String {
        return String.format("%.2f", number)
    }
}