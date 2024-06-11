package com.example.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CocktailDataUI
import com.example.model.mapDomainToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val useCase: RandomUseCase,
) : ViewModel() {
    private val _loadingState = MutableStateFlow<Boolean>(false)
    private val _networkErrorState = MutableStateFlow<Boolean>(false)
    private val _cocktailData = MutableStateFlow<CocktailDataUI?>(null)

    val screenState: StateFlow<RandomScreenState> =
        combine(
            _loadingState,
            _networkErrorState,
            _cocktailData
        ) { loadingState, networkError, data ->
            RandomScreenState(
                isLoading = loadingState,
                networkError = networkError,
                data = data
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RandomScreenState(isLoading = true)
            )

    @Inject
    fun start() {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            useCase.getRandomDetails()
                .onStart {
                    _loadingState.emit(true)
                    _networkErrorState.emit(false)
                }
                .catch {
                    _loadingState.emit(false)
                    _networkErrorState.emit(true)
                }
                .collect {
                    it.onFailure {
                        _loadingState.emit(false)
                        _networkErrorState.emit(true)
                    }
                    it.onSuccess {
                        _loadingState.emit(false)
                        _cocktailData.emit(it.mapDomainToUi())
                    }
                }
        }
    }

}

data class RandomScreenState(
    val isLoading: Boolean = false,
    val networkError: Boolean = false,
    val data: CocktailDataUI? = null
)