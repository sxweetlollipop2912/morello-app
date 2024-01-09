package com.example.morello.ui.create_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.GroupCreate
import com.example.morello.data_layer.data_types.MemberCreate
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreateGroupUiState(
    val group: GroupCreate,
    val membersList: List<String>,
    val submitError: String = "",
    val state: State = State.Idle,
) {

    companion object {
        val new get() = CreateGroupUiState(
            group = GroupCreate(
                name = "",
                description = "",
            ),
            membersList = emptyList(),
        )
    }
}

enum class State {
    Idle,
    Loading,
    Success,
    Error,
    TryToGoBack,
    ConfirmGoBack,
}

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(CreateGroupUiState.new)
    val uiState = _uiState.asStateFlow()

    fun reset() {
        _uiState.update { CreateGroupUiState.new }
    }

    fun onGroupNameChanged(groupName: String) {
        _uiState.update {
            it.copy(
                group = it.group.copy(name = groupName)
            )
        }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update {
            it.copy(
                group = it.group.copy(description = description)
            )
        }
    }

    fun onMembersListChanged(membersList: List<String>) {
        _uiState.update { it.copy(membersList = membersList) }
    }

    fun onSubmit() {
        _uiState.update { it.copy(state = State.Loading) }
        viewModelScope.launch {
            try {
                val groupDetail = groupRepository.createGroup(_uiState.value.group)
                val groupId = groupDetail.id
                _uiState.value.membersList.forEach {
                    groupRepository.createMember(groupId, MemberCreate(it))
                }

                _uiState.update { it.copy(state = State.Success) }
            } catch (e: Exception) {
                _uiState.update { it.copy(state = State.Error, submitError = e.message ?: "") }
            }
        }
    }

    private fun isEmpty(): Boolean {
        val (
            group,
            membersList,
            _,
            _,
        ) = _uiState.value
        return group.name.isEmpty() && group.description.isEmpty() && membersList.isEmpty()
    }

    fun tryToGoBack() {
        if (!isEmpty()) {
            _uiState.update { it.copy(state = State.TryToGoBack) }
        } else {
            _uiState.update { it.copy(state = State.ConfirmGoBack) }
        }
    }

    fun confirmGoBack() {
        if (_uiState.value.state == State.TryToGoBack) {
            _uiState.update { it.copy(state = State.ConfirmGoBack) }
        } else {
            throw IllegalStateException(
                "confirm_go_back() called when state is not TryToGoBack"
            )
        }
    }

    fun cancelGoBack() {
        if (_uiState.value.state == State.TryToGoBack) {
            _uiState.update { it.copy(state = State.Idle) }
        } else {
            throw IllegalStateException(
                "cancel_go_back() called when state is not ConfirmGoBack"
            )
        }
    }
}