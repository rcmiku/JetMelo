package com.rcmiku.music.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcmiku.ncmapi.api.album.AlbumApi
import com.rcmiku.ncmapi.model.AlbumDetailResponse
import com.rcmiku.ncmapi.model.AlbumInfoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val albumId = savedStateHandle.get<Long>("albumId")

    private val _albumDetail =
        MutableStateFlow<Result<AlbumDetailResponse>?>(null)
    val albumDetail: StateFlow<Result<AlbumDetailResponse>?> =
        _albumDetail.asStateFlow()
    private val _albumInfo =
        MutableStateFlow<AlbumInfoResponse?>(null)
    val albumInfo: StateFlow<AlbumInfoResponse?> =
        _albumInfo.asStateFlow()

    init {
        viewModelScope.launch {
            albumId?.let {
                _albumDetail.value = AlbumApi.albumDetail(albumId)
                fetchAlbumInfo()
            }
        }
    }

    private fun fetchAlbumInfo() {
        viewModelScope.launch {
            albumId?.let {
                _albumInfo.value = AlbumApi.albumInfo(albumId).getOrNull()
            }
        }
    }

    fun albumSub(isSub: Boolean) {
        viewModelScope.launch {
            albumId?.let {
                AlbumApi.albumSub(id = albumId, isSub = isSub).onSuccess {
                    fetchAlbumInfo()
                }
            }
        }
    }

}