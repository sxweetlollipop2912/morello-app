package com.example.morello.data_layer.data_sources.data_types

import kotlin.math.roundToInt

typealias Currency = Float

const val CURRENCY_SYMBOL = "₫"

fun Currency.formattedWithSymbol(): String {
    // 100000.25f.formatted() == "100,000"
    // -1000000.0f.formatted() == "-1,000,000"
    return this.formattedNoSymbol() + CURRENCY_SYMBOL
}

fun Currency.formattedNoSymbol(): String {
    // 100000.25f.formatted() == "100,000"
    // -1000000.0f.formatted() == "-1,000,000"
    if (this < 0f) {
        return "-" + (-this).formattedNoSymbol()
    }
    return this.roundToInt().toString().reversed().chunked(3).joinToString(",").reversed()
}

fun formattedStrToCurrency(str: String): Currency {
    // "100,000".formattedStrToCurrency() == 100000.0f
    // "-1,000,000".formattedStrToCurrency() == -1000000.0f
    return str.replace(",", "").replace(CURRENCY_SYMBOL, "").toFloatOrNull() ?: 0f
}