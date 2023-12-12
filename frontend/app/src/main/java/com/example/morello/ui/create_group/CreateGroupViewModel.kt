package com.example.morello.ui.create_group

import com.example.morello.data_layer.repositories.GroupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class CreateGroupUiState(
    val groupName: String = "",
    val membersList: List<String> = emptyList(),
    val isSubmitting: Boolean = false,
    val submitError: String = "",
) {
    companion object {
        val Empty = CreateGroupUiState()
    }
}

class CreateGroupViewModel(
    groupRepository: GroupRepository,
) {
    private var _loginUiState = MutableStateFlow(CreateGroupUiState.Empty)
    val loginUiState = _loginUiState.asStateFlow()

    fun onGroupNameChanged(groupName: String) {
        _loginUiState.value = _loginUiState.value.copy(groupName = groupName)
    }

    fun onMembersListChanged(membersList: List<String>) {
        _loginUiState.value = _loginUiState.value.copy(membersList = membersList)
    }

    fun onSubmit() {
    }
}