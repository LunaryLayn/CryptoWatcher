package com.swissborg.cryptowatcher.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object Util {
    fun Double.formatNumber(): String {
        // Crear un formato para números con separador de miles y dos decimales
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }

        // Configurar el formato para usar separadores de miles y decimales
        val decimalFormat = DecimalFormat("#,##0.00", symbols)

        return if (this % 1.0 == 0.0) {
            // Si el número es entero, formatearlo sin decimales
            decimalFormat.applyPattern("#,##0")
            decimalFormat.format(this)
        } else {
            // Si tiene parte decimal, formatearlo con dos decimales
            decimalFormat.format(this)
        }
    }
}
