package com.example.morello.ui.create_balance_entry

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

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

@HiltViewModel
class CreateIncomeViewModel @Inject constructor() : ViewModel() {
    fun updateAmount(amount: Int) {
    }

    fun updateDescription(description: String) {
    }

}