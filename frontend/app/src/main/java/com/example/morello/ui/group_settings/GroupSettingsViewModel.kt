package com.example.morello.ui.group_settings

import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.GroupDetail
import com.example.morello.data_layer.data_types.GroupUpdate
import com.example.morello.data_layer.data_types.Member
import com.example.morello.data_layer.data_types.MemberCreate
import com.example.morello.data_layer.data_types.Moderator
import com.example.morello.data_layer.data_types.User
import com.example.morello.data_layer.repositories.GroupRepository
import com.example.morello.data_layer.repositories.ModeratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

data class GroupSettingsUiState(
    val group: GroupDetail,
    val members: List<Member>,
    val moderators: List<Moderator>
) {
    companion object {
        val empty = GroupSettingsUiState(
            group = GroupDetail(
                id = -1,
                name = "",
                description = "",
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                recentOpenSessions = emptyList(),
                recentBalanceEntries = emptyList(),
                leader = User(
                    id = -1,
                    name = "",
                    email = "",
                ),
            ),
            members = emptyList(),
            moderators = emptyList(),
        )
    }
}

@HiltViewModel
class GroupSettingsViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val moderatorRepository: ModeratorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val groupId = savedStateHandle.get<Int>(OwnerGroupHomeRoute.groupId)!!

    private var _uiState = MutableStateFlow(GroupSettingsUiState.empty)
    val uiState = _uiState.asStateFlow()

    init {
        reload()
    }

    fun reload() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    group = groupRepository.getGroupDetail(groupId),
                    members = groupRepository.getMembers(groupId),
                    moderators = moderatorRepository.getModerators(groupId)
                )
            }
        }
    }

    fun editGroupInfo(name: String, description: String) {
        viewModelScope.launch {
            groupRepository.updateGroup(
                groupId, GroupUpdate(
                    name = name,
                    description = description
                )
            )
            reload()
        }
    }

    fun addModerator(userEmail: String) {
        viewModelScope.launch {
            moderatorRepository.addModerator(groupId, userEmail)
            reload()
        }
    }

    fun removeModerator(moderatorId: Int) {
        viewModelScope.launch {
            moderatorRepository.removeModerator(groupId, moderatorId)
            reload()
        }
    }

    fun addMember(member: String) {
        viewModelScope.launch {
            groupRepository.createMember(groupId, MemberCreate(member))
            reload()
        }
    }

    fun removeMember(memberId: Int) {
        viewModelScope.launch {
            groupRepository.deleteMember(groupId, memberId)
            reload()
        }
    }
}
