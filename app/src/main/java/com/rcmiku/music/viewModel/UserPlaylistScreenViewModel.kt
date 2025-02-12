package com.rcmiku.music.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcmiku.ncmapi.api.account.AccountApi
import com.rcmiku.ncmapi.api.account.UserPlaylistType
import com.rcmiku.ncmapi.model.UserPlaylistResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPlaylistScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val userId = savedStateHandle.get<Long>("userId")
    private val type = savedStateHandle.get<String>("type")
    val userPlaylistType: UserPlaylistType? =
        type?.let { UserPlaylistType.entries.find { it.type == type } }

    private val _playlist =
        MutableStateFlow<UserPlaylistResponse?>(null)
    val playlist: StateFlow<UserPlaylistResponse?> =
        _playlist.asStateFlow()

    init {
        viewModelScope.launch {
            userId?.let {
                userPlaylistType?.let {
                    _playlist.value = AccountApi.userPlaylist(
                        userId = userId.toLong(),
                        userPlaylistType = userPlaylistType
                    ).getOrNull()
                }
            }
        }
    }
}