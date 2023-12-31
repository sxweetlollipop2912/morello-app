package com.example.morello.ui.create_balance_entry

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.morello.data_layer.data_sources.data_types.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

data class CreateNewSessionData(
    val amountPerMember: Currency,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val memberList: List<Pair<String, Boolean>> = listOf(),
) {
    companion object {
        val new = CreateNewSessionData(
            amountPerMember = 0f,
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now(),
            memberList = listOf(
                Pair("member1", true),
                Pair("member2", true),
                Pair("member3", true),
                Pair("member4", true),
                Pair("member5", true),
                Pair("member6", true),
                Pair("member7", true),
                Pair("member8", false),
                Pair("member9", false),
                Pair("member10", true),

                )
        )
    }

}

enum class Mode {
    CreateNewSession,
    CreateNewEntry,
}

data class StringOrError(
    val value: String,
    val isError: Boolean,
)

data class CreateIncomeUiState(
    val name: StringOrError,
    val description: String,
    val amount: Currency,
    val dateTime: LocalDateTime,
    val createNewSessionData: CreateNewSessionData,
    val state: State = State.Idle,
    val mode: Mode,
    val error: String? = null,
) {
    companion object {
        val newWithCollectSession = CreateIncomeUiState(
            name = StringOrError("", false),
            description = "",
            amount = 0f,
            dateTime = LocalDateTime.now(),
            createNewSessionData = CreateNewSessionData(
                amountPerMember = 0f,
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now(),
            ),
            mode = Mode.CreateNewSession,
        )
        val newWithoutCollectSession = CreateIncomeUiState(
            name = StringOrError("", false),
            description = "",
            amount = 0f,
            dateTime = LocalDateTime.now(),
            mode = Mode.CreateNewEntry,
            createNewSessionData = CreateNewSessionData.new,
        )
    }
}

@HiltViewModel
class CreateIncomeViewModel @Inject constructor() : ViewModel() {
    private var _uiState = MutableStateFlow(
        CreateIncomeUiState.newWithoutCollectSession
    )

    val uiState = _uiState.asStateFlow()

    fun switchToCreateNewSession() {
        _uiState.value = _uiState.value.copy(
            mode = Mode.CreateNewSession,
        )
    }

    fun switchToCreateNewEntry() {
        _uiState.value = _uiState.value.copy(
            mode = Mode.CreateNewEntry,
        )
    }

    fun chosenMembers(): List<String> {
        return _uiState.value.createNewSessionData.memberList
            .filter { it.second }
            .map { it.first }
    }

    private fun chosenMemberCount(): Int {
        return _uiState.value.createNewSessionData.memberList.count { it.second }
    }

    fun updateAmountPerMember(amount: Currency) {
        val newAmount = chosenMemberCount() * amount
        _uiState.value = _uiState.value.copy(
            amount = newAmount,
            createNewSessionData = _uiState.value.createNewSessionData.copy(
                amountPerMember = amount,
            )
        )
    }


    fun updateAmount(amount: Currency) {
        val newAmountPerMember: Currency = calculateAmountPerMember(amount)
        _uiState.value = _uiState.value.copy(
            amount = amount,
            createNewSessionData = _uiState.value.createNewSessionData.copy(
                amountPerMember = newAmountPerMember,
            )
        )
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = StringOrError(name, isError = false))
    }

    private fun calculateAmountPerMember(): Currency {
        val memberCount = _uiState.value.createNewSessionData.memberList.count { it.second }
        return if (memberCount == 0) {
            0f
        } else {
            _uiState.value.amount / memberCount
        }
    }

    private fun calculateAmountPerMember(amount: Currency): Currency {
        val memberCount = _uiState.value.createNewSessionData.memberList.count { it.second }
        return if (memberCount == 0) {
            0f
        } else {
            amount / memberCount
        }
    }

    fun updateChosenMember(index: Int, status: Boolean) {
        if (index < 0 || index >= _uiState.value.createNewSessionData.memberList.size) {
            throw IllegalArgumentException("index out of bound")
        } else if (_uiState.value.createNewSessionData.memberList.isEmpty()) {
            throw IllegalStateException("member list is empty")
        } else if (_uiState.value.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        val memberList = _uiState.value.createNewSessionData.memberList
        val newMemberList = memberList.mapIndexed { i, it ->
            if (i == index) {
                it.copy(second = status)
            } else {
                it
            }
        }
        val newChosenMemberCount = newMemberList.count { it.second }
        _uiState.value = _uiState.value.copy(
            createNewSessionData = _uiState.value.createNewSessionData.copy(
                amountPerMember = _uiState.value.amount / newChosenMemberCount,
                memberList = newMemberList,
            )
        )
    }

    fun updateDateTime(dateTime: LocalDateTime) {
        _uiState.value = _uiState.value.copy(dateTime = dateTime)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun reset() {
        _uiState.value = CreateIncomeUiState.newWithoutCollectSession
    }

    private fun isEmpty(): Boolean {
        val uiState = _uiState.value
        return uiState.name.value.isEmpty() &&
                uiState.description.isEmpty() &&
                uiState.amount == 0f
    }

    fun tryToGoBack() {
        if (!isEmpty()) {
            _uiState.value = _uiState.value.copy(state = State.TryToGoBack)
        } else {
            _uiState.value = _uiState.value.copy(state = State.ConfirmGoBack)
        }
    }

    fun updateStartDateTime(dateTime: LocalDateTime) {
        Log.d("CreateIncomeViewModel", "updateStartDateTime: $dateTime")
        if (_uiState.value.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        _uiState.value = _uiState.value.copy(
            createNewSessionData = _uiState.value.createNewSessionData.copy(
                startDate = dateTime,
            )
        )
    }

    fun updateEndDateTime(dateTime: LocalDateTime) {
        if (_uiState.value.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        _uiState.value = _uiState.value.copy(
            createNewSessionData = _uiState.value.createNewSessionData.copy(
                endDate = dateTime,
            )
        )
    }

    fun submit(groupId: Int) {
        if (_uiState.value.name.value.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                name = StringOrError(
                    value = "Name should not be empty",
                    isError = true,
                ),
                state = State.Error,
                error = "Name cannot be empty",
            )
            return
        }
        if (_uiState.value.mode == Mode.CreateNewSession) {
            submitCollectSession(groupId)
        } else {
            submitNewEntry(groupId)
        }
    }

    private fun submitCollectSession(groupId: Int) {
        // TODO:  
    }
    
    private fun submitNewEntry(groupId: Int) {
        // TODO:  
    }
    

    fun confirmGoBack() {
        if (_uiState.value.state == State.TryToGoBack) {
            _uiState.value = _uiState.value.copy(state = State.ConfirmGoBack)
        } else {
            throw IllegalStateException(
                "confirm_go_back() called when state is not TryToGoBack"
            )
        }
    }

    fun cancelGoBack() {
        if (_uiState.value.state == State.TryToGoBack) {
            _uiState.value = _uiState.value.copy(state = State.Idle)
        } else {
            throw IllegalStateException(
                "cancel_go_back() called when state is not ConfirmGoBack"
            )
        }
    }

}