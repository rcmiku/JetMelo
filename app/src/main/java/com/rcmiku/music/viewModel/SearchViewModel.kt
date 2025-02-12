package com.rcmiku.music.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rcmiku.music.data.searchHistoryDataStore
import com.rcmiku.music.paging.SearchPagingSource
import com.rcmiku.ncmapi.api.search.SearchApi
import com.rcmiku.ncmapi.api.search.SearchType
import com.rcmiku.ncmapi.model.SearchSuggestKeywordResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    private val _searchType = MutableStateFlow<SearchType>(SearchType.Song)
    val searchType: StateFlow<SearchType> = _searchType.asStateFlow()
    private val _searchValue = MutableStateFlow("")
    private val _suggestKeywordResponse = MutableStateFlow<SearchSuggestKeywordResponse?>(null)
    val suggestKeywordResponse: StateFlow<SearchSuggestKeywordResponse?> =
        _suggestKeywordResponse.asStateFlow()

    fun updateSearchType(searchType: SearchType) {
        _searchType.value = searchType
    }

    fun updateSearchValue(searchValue: String) {
        _searchValue.value = searchValue
        saveSearch(searchValue)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults = _searchValue.filter { it.isNotEmpty() }
        .combine(_searchType) { keyword, searchType ->
            Pager(
                config = PagingConfig(
                    pageSize = 100,
                    prefetchDistance = 50,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { SearchPagingSource(keyword, searchType) }
            ).flow
        }
        .flatMapLatest { it }
        .cachedIn(viewModelScope)

    fun fetchSearchKeyword(searchValue: String) {
        viewModelScope.launch {
            _suggestKeywordResponse.value = SearchApi.searchSuggestKeyword(searchValue).getOrNull()
        }
    }

    val searchHistory: Flow<List<String>> = getSearchHistory(context).stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    private fun saveSearch(query: String) {
        viewModelScope.launch {
            saveSearchQuery(context, query)
        }
    }

    private suspend fun saveSearchQuery(context: Context, query: String) {
        context.searchHistoryDataStore.updateData { currentHistory ->
            val historyList = currentHistory.historyList.toMutableList()
            historyList.remove(query)
            historyList.add(0, query)
            if (historyList.size > 10) historyList.removeAt(historyList.lastIndex)
            currentHistory.toBuilder().clearHistory().addAllHistory(historyList).build()
        }
    }

    private fun getSearchHistory(context: Context): Flow<List<String>> {
        return context.searchHistoryDataStore.data.map { it.historyList }
    }

    fun deleteSearchQuery(query: String) {
        viewModelScope.launch {
            removeSearchQuery(context, query)
        }
    }

    private suspend fun removeSearchQuery(context: Context, query: String) {
        context.searchHistoryDataStore.updateData { currentHistory ->
            val historyList = currentHistory.historyList.toMutableList()
            historyList.remove(query)
            currentHistory.toBuilder().clearHistory().addAllHistory(historyList).build()
        }
    }

}