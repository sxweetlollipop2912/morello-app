package com.example.morello.data_layer.data_sources.data_types

import kotlin.math.roundToInt

typealias Currency = Int

fun Currency.formatted(): String {
    // 100000.25f.formatted() == "100,000"
    // 1000000.0f.formatted() == "1,000,000"
    return this.toString().reversed().chunked(3).joinToString(",").reversed()
}

fun formattedStrToCurrency(str: String): Currency {
    // "100,000".formattedStrToCurrency() == 100000.0f
    // "1,000,000".formattedStrToCurrency() == 1000000.0f
    return str.replace(",", "").toIntOrNull() ?: 0
}