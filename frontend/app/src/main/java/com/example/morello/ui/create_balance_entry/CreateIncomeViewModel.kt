package com.example.morello.ui.create_balance_entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_sources.data_types.Currency
import com.example.morello.data_layer.data_sources.data_types.balance.NewBalanceEntryRequest
import com.example.morello.data_layer.data_sources.data_types.collect_sessions.NewCollectSession
import com.example.morello.data_layer.repositories.CollectSessionRepository
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
class CreateIncomeViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val collectSessionRepository: CollectSessionRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CreateIncomeUiState.newWithoutCollectSession)
        private set

    fun switchToCreateNewSession() {
        uiState = uiState.copy(
            mode = Mode.CreateNewSession,
        )
    }

    fun switchToCreateNewEntry() {
        uiState = uiState.copy(
            mode = Mode.CreateNewEntry,
        )
    }

    fun chosenMembers(): List<String> {
        return uiState.createNewSessionData.memberList
            .filter { it.second }
            .map { it.first }
    }

    private fun chosenMemberCount(): Int {
        return uiState.createNewSessionData.memberList.count { it.second }
    }

    fun updateAmountPerMember(amount: Currency) {
        val newAmount = chosenMemberCount() * amount
        uiState = uiState.copy(
            amount = newAmount,
            createNewSessionData = uiState.createNewSessionData.copy(
                amountPerMember = amount,
            )
        )
    }


    fun updateAmount(amount: Currency) {
        val newAmountPerMember: Currency = calculateAmountPerMember(amount)
        uiState = uiState.copy(
            amount = amount,
            createNewSessionData = uiState.createNewSessionData.copy(
                amountPerMember = newAmountPerMember,
            )
        )
    }

    fun updateName(name: String) {
        uiState = uiState.copy(name = StringOrError(name, isError = false))
    }

    private fun calculateAmountPerMember(): Currency {
        val memberCount = uiState.createNewSessionData.memberList.count { it.second }
        return if (memberCount == 0) {
            0f
        } else {
            uiState.amount / memberCount
        }
    }

    private fun calculateAmountPerMember(amount: Currency): Currency {
        val memberCount = uiState.createNewSessionData.memberList.count { it.second }
        return if (memberCount == 0) {
            0f
        } else {
            amount / memberCount
        }
    }

    fun updateChosenMember(index: Int, status: Boolean) {
        if (index < 0 || index >= uiState.createNewSessionData.memberList.size) {
            throw IllegalArgumentException("index out of bound")
        } else if (uiState.createNewSessionData.memberList.isEmpty()) {
            throw IllegalStateException("member list is empty")
        } else if (uiState.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        val memberList = uiState.createNewSessionData.memberList
        val newMemberList = memberList.mapIndexed { i, it ->
            if (i == index) {
                it.copy(second = status)
            } else {
                it
            }
        }
        val newChosenMemberCount = newMemberList.count { it.second }
        uiState = uiState.copy(
            createNewSessionData = uiState.createNewSessionData.copy(
                amountPerMember = uiState.amount / newChosenMemberCount,
                memberList = newMemberList,
            )
        )
    }

    fun updateDateTime(dateTime: LocalDateTime) {
        uiState = uiState.copy(dateTime = dateTime)
    }

    fun updateDescription(description: String) {
        uiState = uiState.copy(description = description)
    }

    fun reset() {
        uiState = CreateIncomeUiState.newWithoutCollectSession
    }

    private fun isEmpty(): Boolean {
        val uiState = uiState
        return uiState.name.value.isEmpty() &&
                uiState.description.isEmpty() &&
                uiState.amount == 0f
    }

    fun tryToGoBack() {
        uiState = if (!isEmpty()) {
            uiState.copy(state = State.TryToGoBack)
        } else {
            uiState.copy(state = State.ConfirmGoBack)
        }
    }

    fun updateStartDateTime(dateTime: LocalDateTime) {
        if (uiState.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        uiState = uiState.copy(
            createNewSessionData = uiState.createNewSessionData.copy(
                startDate = dateTime,
            )
        )
    }

    fun updateEndDateTime(dateTime: LocalDateTime) {
        if (uiState.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        uiState = uiState.copy(
            createNewSessionData = uiState.createNewSessionData.copy(
                endDate = dateTime,
            )
        )
    }

    fun submit(groupId: Int) {
        if (uiState.name.value.isEmpty()) {
            uiState = uiState.copy(
                name = StringOrError(
                    value = "Name should not be empty",
                    isError = true,
                ),
                error = "Name cannot be empty",
            )
            return
        }
        if (uiState.mode == Mode.CreateNewSession) {
            submitCollectSession(groupId)
        } else {
            submitNewEntry(groupId)
        }
    }

    private fun submitCollectSession(groupId: Int) {
        uiState = uiState.copy(state = State.Submitting)
        viewModelScope.launch {
            uiState = try {
                collectSessionRepository.createCollectSession(
                    groupId = groupId,
                    collectSession = uiState.let {
                        NewCollectSession(
                            name = it.name.value,
                            description = it.description,
                            start = it.createNewSessionData.startDate.toString(),
                            due = it.createNewSessionData.endDate.toString(),
                            isOpen = true,
                            paymentPerMember = it.createNewSessionData.amountPerMember,
                        )
                    },
                )
                uiState.copy(
                    state = State.Success,
                )
            } catch (e: Exception) {
                uiState.copy(
                    error = e.message,
                )
            }
        }
    }

    private fun submitNewEntry(groupId: Int) {
        uiState = uiState.copy(state = State.Submitting)
        viewModelScope.launch {
            try {
                groupRepository.createBalanceEntry(
                    groupId = groupId,
                    balanceEntry = NewBalanceEntryRequest(
                        name = uiState.name.value,
                        description = uiState.description,
                        amount = uiState.amount,
                        createdAt = LocalDateTime.now(),
                    )
                )
                uiState = uiState.copy(
                    state = State.Success,
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e.message,
                )
            }
        }
    }


    fun confirmGoBack() {
        if (uiState.state == State.TryToGoBack) {
            uiState = uiState.copy(state = State.ConfirmGoBack)
        } else {
            throw IllegalStateException(
                "confirm_go_back() called when state is not TryToGoBack"
            )
        }
    }

    fun cancelGoBack() {
        if (uiState.state == State.TryToGoBack) {
            uiState = uiState.copy(state = State.Idle)
        } else {
            throw IllegalStateException(
                "cancel_go_back() called when state is not ConfirmGoBack"
            )
        }
    }

}