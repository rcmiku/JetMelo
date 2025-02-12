package com.rcmiku.music.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rcmiku.music.paging.CloudPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CloudSongScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {

    val uid = savedStateHandle.get<Long>("uid")

    val cloudSong = Pager(
        config = PagingConfig(
            pageSize = 500,
            prefetchDistance = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CloudPagingSource() }
    ).flow.cachedIn(viewModelScope)
}