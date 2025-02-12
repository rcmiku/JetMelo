package com.rcmiku.music.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rcmiku.music.paging.AlbumPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumSublistScreenViewmodel @Inject constructor() : ViewModel() {

    val albumSublist = Pager(
        config = PagingConfig(
            pageSize = 100,
            prefetchDistance = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { AlbumPagingSource() }
    ).flow.cachedIn(viewModelScope)


}