package com.example.morello.ui.create_balance_entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.BalanceEntryCreate
import com.example.morello.data_layer.data_types.CollectSessionCreate
import com.example.morello.data_layer.data_types.Currency
import com.example.morello.data_layer.repositories.CollectSessionRepository
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

data class MemberEntryData(
    val id: Int,
    val name: String,
    val selected: Boolean,
)

data class CreateNewSessionData(
    val amountPerMember: Currency,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val memberList: List<MemberEntryData> = listOf(),
) {
    companion object {
        fun new(memberList: List<MemberEntryData> = listOf()) = CreateNewSessionData(
            amountPerMember = 0,
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            memberList = memberList,
        )
    }

}

enum class Mode {
    CreateNewSession,
    CreateNewEntry,
}

data class StringOrError(
    val value: String,
    val error: String?
)

data class CreateIncomeUiState(
    val name: StringOrError,
    val description: String,
    val amount: Currency,
    val balanceAfter: Currency?,
    val dateTime: OffsetDateTime,
    val createNewSessionData: CreateNewSessionData,
    val state: State = State.Uninitialized,
    val mode: Mode,
    val error: String? = null,
    val dateTimeError: String? = null,
) {
    companion object {
        val newWithCollectSession = CreateIncomeUiState(
            name = StringOrError("", null),
            description = "",
            amount = 0,
            balanceAfter = null,
            dateTime = OffsetDateTime.now(),
            createNewSessionData = CreateNewSessionData(
                amountPerMember = 0,
                startDate = OffsetDateTime.now(),
                endDate = OffsetDateTime.now(),
            ),
            mode = Mode.CreateNewSession,
        )

        fun newWithoutCollectSession(
            memberList: List<MemberEntryData> = listOf(),
        ) = CreateIncomeUiState(
            balanceAfter = null,
            name = StringOrError("", null),
            description = "",
            amount = 0,
            dateTime = OffsetDateTime.now(),
            mode = Mode.CreateNewEntry,
            createNewSessionData = CreateNewSessionData.new(
                memberList = memberList,
            ),
        )
    }

    fun consideredEmpty(): Boolean {
        return name.value.isEmpty() &&
                description.isEmpty() &&
                amount == 0
    }
}

@HiltViewModel
class CreateIncomeViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val collectSessionRepository: CollectSessionRepository,
) : ViewModel() {
    var uiState by mutableStateOf(
        CreateIncomeUiState.newWithoutCollectSession()
    )

    private var groupCurrentBalance: Currency? = null

    suspend fun init(groupId: Int) {
        val currentBalance = groupRepository.getGroupBalance(groupId).currentBalance
        val state = CreateIncomeUiState.newWithoutCollectSession(
            groupRepository.getMembers(groupId).map {
                MemberEntryData(
                    id = it.id,
                    name = it.name,
                    selected = true,
                )
            }
        )
        groupCurrentBalance = currentBalance
        uiState = state.copy(state = State.Idle, balanceAfter = currentBalance)
    }

    fun switchToCreateNewSession() {
        uiState = uiState.copy(
            mode = Mode.CreateNewSession,
        )
    }

    fun dismissDateTimeError() {
        uiState = uiState.copy(
            dateTimeError = null,
        )
    }

    fun switchToCreateNewEntry() {
        uiState = uiState.copy(
            mode = Mode.CreateNewEntry,
        )
    }

    fun chosenMembers(): List<MemberEntryData> {
        return uiState.createNewSessionData.memberList
            .filter { it.selected }
    }

    private fun chosenMemberCount(): Int {
        return uiState.createNewSessionData.memberList.count { it.selected }
    }

    fun updateAmountPerMember(amount: Currency) {
        val newAmount = chosenMemberCount() * amount
        uiState = uiState.copy(
            amount = newAmount,
            balanceAfter = groupCurrentBalance?.plus(newAmount) ?: 0,
            createNewSessionData = uiState.createNewSessionData.copy(
                amountPerMember = amount,
            )
        )
    }


    fun updateAmount(amount: Currency) {
        val newAmountPerMember: Currency = calculateAmountPerMember(amount)
        uiState = uiState.copy(
            amount = amount,
            balanceAfter = groupCurrentBalance?.plus(amount) ?: 0,
            createNewSessionData = uiState.createNewSessionData.copy(
                amountPerMember = newAmountPerMember,
            )
        )
    }

    fun updateName(name: String) {
        uiState = uiState.copy(name = uiState.name.copy(value = name))
    }

    fun finish() {
        uiState = uiState.copy(state = State.Uninitialized)
    }

    private fun calculateAmountPerMember(amount: Currency): Currency {
        val memberCount = uiState.createNewSessionData.memberList.count { it.selected }
        return if (memberCount == 0) {
            0
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
                it.copy(selected = status)
            } else {
                it
            }
        }
        val newChosenMemberCount = newMemberList.count { it.selected }
        val newAmountPerMember = if (newChosenMemberCount == 0) {
            0
        } else {
            uiState.amount / newChosenMemberCount
        }
        uiState = uiState.copy(
            createNewSessionData = uiState.createNewSessionData.copy(
                amountPerMember = newAmountPerMember,
                memberList = newMemberList,
            )
        )
    }

    fun updateDateTime(dateTime: OffsetDateTime) {
        uiState = uiState.copy(dateTime = dateTime)
    }

    fun updateDescription(description: String) {
        uiState = uiState.copy(description = description)
    }

    private fun isEmpty(): Boolean {
        val uiState = uiState
        return uiState.name.value.isEmpty() &&
                uiState.description.isEmpty() &&
                uiState.amount == 0
    }

    fun updateStartDateTime(dateTime: OffsetDateTime) {
        if (uiState.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        uiState = if (dateTime.isAfter(uiState.createNewSessionData.endDate)) {
            uiState.copy(
                dateTimeError = "Start date cannot be after end date",
            )
        } else {
            uiState.copy(
                createNewSessionData = uiState.createNewSessionData.copy(
                    startDate = dateTime,
                )
            )
        }
    }

    fun updateEndDateTime(dateTime: OffsetDateTime) {
        if (uiState.mode != Mode.CreateNewSession) {
            throw IllegalStateException("mode is not CreateNewSession")
        }
        uiState = if (dateTime.isBefore(uiState.createNewSessionData.startDate)) {
            uiState.copy(
                dateTimeError = "End date cannot be before start date",
            )
        } else {
            uiState.copy(
                createNewSessionData = uiState.createNewSessionData.copy(
                    endDate = dateTime,
                )
            )
        }
    }

    fun submit(groupId: Int) {
        if (uiState.name.value.isEmpty()) {
            uiState = uiState.copy(
                name = uiState.name.copy(
                    error = "Name should not be empty",
                ),
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
                    collectSession = uiState.let { incomeUiState ->
                        CollectSessionCreate(
                            name = incomeUiState.name.value,
                            description = incomeUiState.description,
                            start = incomeUiState.createNewSessionData.startDate,
                            due = incomeUiState.createNewSessionData.endDate,
                            paymentPerMember = incomeUiState.createNewSessionData.amountPerMember,
                            memberIds = incomeUiState.createNewSessionData.memberList
                                .filter { it.selected }
                                .map { it.id },
                        )
                    },
                )
                uiState.copy(
                    state = State.Success,
                )
            } catch (e: Exception) {
                uiState.copy(
                    state = State.Error,
                    error = e.message,
                )
            }
        }
    }

    private fun submitNewEntry(groupId: Int) {
        uiState = uiState.copy(state = State.Submitting)
        viewModelScope.launch {
            uiState = try {
                groupRepository.createBalanceEntry(
                    groupId = groupId,
                    balanceEntry = uiState.let {
                        BalanceEntryCreate(
                            name = it.name.value,
                            description = it.description,
                            amount = it.amount,
                            recordedAt = OffsetDateTime.now(),
                        )
                    },
                )
                uiState.copy(
                    state = State.Success,
                )
            } catch (e: Exception) {
                uiState.copy(
                    state = State.Error,
                    error = e.message,
                )
            }
        }
    }
}