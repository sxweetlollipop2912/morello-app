package com.example.morello.ui.create_group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.repositories.GroupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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
    private val groupRepository: GroupRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(CreateGroupUiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun onGroupNameChanged(groupName: String) {
        _uiState.value = _uiState.value.copy(groupName = groupName)
    }

    fun onMembersListChanged(membersList: List<String>) {
        _uiState.value = _uiState.value.copy(membersList = membersList)
    }

    fun onSubmit() {
        _uiState.value = _uiState.value.copy(isSubmitting = true)
        viewModelScope.launch {
            groupRepository.createNewGroup(
                newGroup = Group(
                    id = 0,
                    name = _uiState.value.groupName,
                    description = "",
                ),
                members = listOf()
            )
        }
    }
}