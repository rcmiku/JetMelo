package com.rcmiku.music.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rcmiku.music.paging.RadioPagingSource
import com.rcmiku.ncmapi.api.radio.RadioApi
import com.rcmiku.ncmapi.model.RadioInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramRadioScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val radioId = savedStateHandle.get<Long>("radioId")

    private val _radioInfo =
        MutableStateFlow<RadioInfoResponse?>(null)
    val radioInfo: StateFlow<RadioInfoResponse?> =
        _radioInfo.asStateFlow()


    init {
        viewModelScope.launch {
            radioId?.let {
                _radioInfo.value = RadioApi.radioInfo(radioId = radioId).getOrNull()
            }
        }
    }


    val radioList = radioId?.let { id ->
        Pager(
            config = PagingConfig(
                pageSize = 500,
                prefetchDistance = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RadioPagingSource(id) }
        ).flow.cachedIn(viewModelScope)
    } ?: flowOf()

}