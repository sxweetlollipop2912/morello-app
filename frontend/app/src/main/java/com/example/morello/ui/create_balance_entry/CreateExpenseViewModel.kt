package com.example.morello.ui.create_balance_entry

import androidx.lifecycle.ViewModel
import java.util.Date

data class CreateExpenseUiState(
    val amount: Int,
    val balanceAfter: Int,
    val name: String,
    val description: String,
    val dateTime: Date,
)

class CreateExpenseViewModel : ViewModel() {
}