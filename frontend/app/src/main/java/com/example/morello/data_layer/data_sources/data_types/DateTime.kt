package com.example.morello.data_layer.data_sources.data_types

import java.time.LocalDateTime

fun LocalDateTime.formattedNoTime(): String {
    // "2021-10-01T00:00:00".formatted() == "1 Dec 2021"
    // "2021-10-01T12:34:56".formatted() == "1 Dec 2021"
    return "${this.dayOfMonth} ${
        this.month.name.substring(0, 3).lowercase().replaceFirstChar { it.uppercase() }
    } ${this.year}"
}