package com.rcmiku.music.viewModel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcmiku.music.data.favoriteSongIdsDatastore
import com.rcmiku.ncmapi.api.account.AccountApi
import com.rcmiku.ncmapi.api.playlist.PlaylistApi
import com.rcmiku.ncmapi.model.PlaylistDetailResponse
import com.rcmiku.ncmapi.model.PlaylistInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    private val playlistId = savedStateHandle.get<Long>("playlistId")
    private val limit = savedStateHandle.get<Int>("limit")
    private val noCache = savedStateHandle.get<Boolean>("noCache") ?: false
    private val _playlistDetail =
        MutableStateFlow<PlaylistDetailResponse?>(null)
    val playlistDetail: StateFlow<PlaylistDetailResponse?> =
        _playlistDetail.asStateFlow()

    private val _playlistInfo =
        MutableStateFlow<PlaylistInfoResponse?>(null)
    val playlistInfo: StateFlow<PlaylistInfoResponse?> =
        _playlistInfo.asStateFlow()

    init {
        viewModelScope.launch {
            if (noCache)
                fetchWithObserver()
            else
                playlistId?.let {
                    limit?.let {
                        _playlistDetail.value = PlaylistApi.playlistDetail(
                            id = playlistId,
                            limit = limit,
                        ).getOrNull()
                    }
                    fetchPlaylistInfo()
                }
        }
    }

    private fun fetchWithObserver() {
        viewModelScope.launch {
            context.favoriteSongIdsDatastore.data.distinctUntilChanged().collectLatest {
                playlistId?.let {
                    AccountApi.favoriteSongLikeChange().onSuccess {
                        _playlistDetail.value = PlaylistApi.playlistV6Detail(
                            id = playlistId,
                        ).getOrNull()
                    }
                }
            }
        }
    }

    private fun fetchPlaylistInfo() {
        viewModelScope.launch {
            playlistId?.let {
                _playlistInfo.value = PlaylistApi.playlistInfo(it).getOrNull()
            }
        }
    }

    fun playlistSub(isSub: Boolean) {
        viewModelScope.launch {
            playlistId?.let {
                PlaylistApi.playlistSub(id = it, isSub = isSub).onSuccess {
                    fetchPlaylistInfo()
                }
            }
        }
    }

}