package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CocktailDataUI
import com.example.model.mapDomainToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase,
) : ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    private val _networkState = MutableStateFlow(false)
    private val _searchRequest = MutableStateFlow("")

    private val _searchResponse = MutableStateFlow<List<CocktailDataUI>>(listOf())

    private val _screenEffect = MutableSharedFlow<SearchScreenEffect>()
    val screenEffect = _screenEffect.asSharedFlow()

    val screenState = combine(
        _loadingState,
        _searchRequest,
        _networkState,
        _searchResponse,
    ) { loading, searchRequest, networkError, searchResponse ->
        SearchScreenState(
            isLoading = loading,
            isNetworkError = networkError,
            searchRequest = searchRequest,
            cocktailsList = searchResponse
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchScreenState(searchRequest = "", cocktailsList = emptyList())
    )

    init {
        viewModelScope.launch {
            _searchRequest
                .debounce(400)
                .collect { newValue ->
                    fetchNewData(newValue)
                }
        }

    }

    fun onAction(action: SearchScreenAction) {
        when (action) {
            is SearchScreenAction.NavigateToDetails -> {
                useCase.setSelectItem(action.id)
                viewModelScope.launch {
                    _screenEffect.emit(SearchScreenEffect.NavToDetails)
                }
            }

            is SearchScreenAction.RequestChanged -> onRequestChanged(action.newRequest)
            SearchScreenAction.Update -> fetchData()
        }
    }

    private fun onRequestChanged(newValue: String) {
        _searchRequest.update { newValue }
    }

    private fun fetchData() {
        fetchNewData(_searchRequest.value)
    }


    private fun fetchNewData(newValue: String) {
        if (newValue.isNotBlank()) {
            viewModelScope.launch {
                useCase.search(request = newValue)
                    .onStart {
                        _loadingState.emit(true)
                        _networkState.emit(false)
                    }
                    .catch {
                        _loadingState.emit(false)
                        _networkState.emit(true)
                    }
                    .collect {
                        it.onFailure {
                            _loadingState.emit(false)
                            _networkState.emit(true)
                        }
                        it.onSuccess {
                            _loadingState.emit(false)
                            _searchResponse.emit(it.mapDomainToUi())
                        }
                    }
            }
        }
    }

}

sealed class SearchScreenEffect {
    data object None : SearchScreenEffect()
    data object NavToDetails : SearchScreenEffect()
}

sealed class SearchScreenAction {
    data object Update : SearchScreenAction()
    data class RequestChanged(val newRequest: String) : SearchScreenAction()
    data class NavigateToDetails(val id: Int) : SearchScreenAction()
}

data class SearchScreenState(
    val isLoading: Boolean = false,
    val isNetworkError: Boolean = false,
    val searchRequest: String = "",
    val cocktailsList: List<CocktailDataUI> = emptyList(),
)