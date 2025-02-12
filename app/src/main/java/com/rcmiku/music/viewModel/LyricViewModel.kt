package com.rcmiku.music.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcmiku.ncmapi.api.player.PlayerApi
import com.rcmiku.ncmapi.model.LyricResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor() : ViewModel() {

    private val _lyric = MutableStateFlow<LyricResponse?>(null)
    val lyric: StateFlow<LyricResponse?> = _lyric.asStateFlow()

    fun fetchLyric(musicId: Long) {
        viewModelScope.launch {
            _lyric.value = PlayerApi.songLyric(musicId).getOrNull()
        }
    }
}