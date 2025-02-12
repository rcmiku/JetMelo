package com.rcmiku.music.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcmiku.ncmapi.api.account.AccountApi
import com.rcmiku.ncmapi.model.FavoriteSongResponse
import com.rcmiku.ncmapi.model.UserInfoBatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel @Inject constructor() : ViewModel() {
    private val _userInfo = MutableStateFlow<UserInfoBatch?>(null)
    val userInfo: StateFlow<UserInfoBatch?> = _userInfo.asStateFlow()

    private val _favoriteSong = MutableStateFlow<FavoriteSongResponse?>(null)
    val favoriteSong: StateFlow<FavoriteSongResponse?> = _favoriteSong.asStateFlow()

    init {
        observeUserIdChanges()
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            _userInfo.value = AccountApi.accountInfo().getOrNull()
        }
    }

    private fun fetchFavoriteSong(userId: Long) {
        viewModelScope.launch {
            _favoriteSong.value = AccountApi.favoriteSong(userId).getOrNull()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserIdChanges() {
        viewModelScope.launch {
            userInfo
                .mapLatest { it?.account?.profile?.userId }
                .distinctUntilChanged()
                .collectLatest { userId ->
                    userId?.let { fetchFavoriteSong(it) }
                }
        }
    }
}
