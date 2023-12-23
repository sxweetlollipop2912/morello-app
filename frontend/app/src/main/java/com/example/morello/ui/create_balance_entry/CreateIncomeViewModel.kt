package com.example.morello.ui.create_balance_entry

import androidx.lifecycle.ViewModel
import java.time.LocalDateTime

data class CreateNewSessionData(
    val name: String,
    val description: String,
    val amountPerMember: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)

data class CreateIncomeEntryData(
    val name: String,
    val description: String,
    val amount: Int,
    val dateTime: LocalDateTime,
)

class CreateIncomeViewModel : ViewModel() {
    fun updateAmount(amount: Int) {
    }

    fun updateDescription(description: String) {
    }

}