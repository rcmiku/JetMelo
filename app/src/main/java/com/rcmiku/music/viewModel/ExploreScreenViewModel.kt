package com.rcmiku.music.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcmiku.ncmapi.api.playlist.PlaylistApi
import com.rcmiku.ncmapi.api.recommend.RecommendApi
import com.rcmiku.ncmapi.model.NewAlbumResponse
import com.rcmiku.ncmapi.model.TopListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreScreenViewModel @Inject constructor() : ViewModel() {

    private val _topList =
        MutableStateFlow<Result<TopListResponse>?>(null)
    val topList: StateFlow<Result<TopListResponse>?> =
        _topList.asStateFlow()

    private val _newAlbum =
        MutableStateFlow<NewAlbumResponse?>(null)
    val newAlbum: StateFlow<NewAlbumResponse?> =
        _newAlbum.asStateFlow()

    private fun fetchTopList() {
        viewModelScope.launch {
            _topList.value = PlaylistApi.topList()
        }
    }

    private fun fetchNewAlbum() {
        viewModelScope.launch {
            _newAlbum.value = RecommendApi.newAlbum().getOrNull()
        }
    }

    init {
        fetchTopList()
        fetchNewAlbum()
    }
}