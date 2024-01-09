package com.example.morello.ui.authorized_home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.Group
import com.example.morello.data_layer.data_types.User
import com.example.morello.data_layer.repositories.GroupRepository
import com.example.morello.data_layer.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthorizedHomeUiState(
    val user: User,
    val groups: List<Group> = emptyList(),
    val searchQuery: String = "",
    val state: State = State.Idle,
    val error: String?,
) {
    companion object {
        val empty = AuthorizedHomeUiState(
            user = User(
                id = 0,
                name = "",
                email = "",
            ),
            groups = emptyList(),
            error = null,
        )
    }
}

enum class State {
    Idle,
    Loading,
    Success,
    Error
}

@HiltViewModel
class AuthorizedHomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
) : ViewModel() {
    private var _groups = MutableStateFlow<List<Group>>(emptyList())
    private var _uiState = MutableStateFlow(AuthorizedHomeUiState.empty)

    val uiState = _uiState.combine(_groups) { uiState, groups ->
        val filteredGroups = groups.filter {
            it.name.contains(uiState.searchQuery, ignoreCase = true)
        }
        uiState.copy(
            groups = filteredGroups,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        AuthorizedHomeUiState.empty,
    )

    init {
        refreshUiState()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onLogout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun refreshUiState() {
        viewModelScope.launch {
            _uiState.update { it.copy(state = State.Loading) }
            try {
                _uiState.update { it.copy(user = userRepository.fetchUserDetail()) }
            } catch (e: Exception) {
                _uiState.update { it.copy(state = State.Error, error = e.message) }
            }
            try {
                _groups.update { groupRepository.getGroups() }
            } catch (e: Exception) {
                _uiState.update { it.copy(state = State.Error, error = e.message) }
            }
            _uiState.update { it.copy(state = State.Success) }
        }
    }
}
