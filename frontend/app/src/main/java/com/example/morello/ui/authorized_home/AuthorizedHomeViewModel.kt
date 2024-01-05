package com.example.morello.ui.authorized_home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_sources.data_types.group.Group
import com.example.morello.data_layer.data_types.User
import com.example.morello.data_layer.repositories.GroupRepository
import com.example.morello.data_layer.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class AuthorizedHomeUiState(
    val user: User,
    val groups: List<Group> = emptyList(),
    val state: State = State.Idle,
    val error: String? = null,
) {
    companion object {
        val Empty = AuthorizedHomeUiState(
            user = User(
                id = 0,
                name = "",
                email = "",
                createdAt = LocalDateTime.MIN,
            )
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
    private var _uiState = MutableStateFlow(AuthorizedHomeUiState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        reload()
    }

    fun reload() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(state = State.Loading)
            try {
                val user = userRepository.fetchUserDetail()
                _uiState.value = _uiState.value.copy(user = user)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(state = State.Error, error = e.message)
            }
            try {
                val groups = groupRepository.getManagedGroups()
                _uiState.value = _uiState.value.copy(groups = groups, state = State.Success)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(state = State.Error, error = e.message)
            }
        }
    }

}
