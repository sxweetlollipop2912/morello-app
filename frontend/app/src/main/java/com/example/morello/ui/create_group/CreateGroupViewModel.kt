package com.example.morello.ui.create_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_sources.CreateGroupException
import com.example.morello.data_layer.data_sources.data_types.NewGroup
import com.example.morello.data_layer.data_sources.data_types.NewMember
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreateGroupUiState(
    val groupName: String = "",
    val membersList: List<String> = emptyList(),
    val submitError: String = "",
    val state: State = State.Idle,
) {

    companion object {
        val Empty = CreateGroupUiState()
    }
}

enum class State {
    Idle,
    Loading,
    Success,
    Error
}

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(CreateGroupUiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun reload() {
        _uiState.value = CreateGroupUiState.Empty
    }

    fun onGroupNameChanged(groupName: String) {
        _uiState.value = _uiState.value.copy(groupName = groupName)
    }

    fun onMembersListChanged(membersList: List<String>) {
        _uiState.value = _uiState.value.copy(membersList = membersList)
    }

    fun onSubmit() {
        _uiState.value = _uiState.value.copy(state = State.Loading)
        viewModelScope.launch {
            try {
                groupRepository.createNewGroup(
                    newGroup = NewGroup(
                        name = _uiState.value.groupName,
                        description = "",
                    ),
                    members = _uiState.value.membersList.map {
                        NewMember(it)
                    }
                )
                _uiState.value = _uiState.value.copy(state = State.Success)
            } catch (e: CreateGroupException) {
                _uiState.value =
                    _uiState.value.copy(state = State.Error, submitError = e.message ?: "")
            }
        }
    }
}