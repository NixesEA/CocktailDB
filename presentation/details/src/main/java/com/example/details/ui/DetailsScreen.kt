@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.Content
import com.example.details.DetailsScreenState
import com.example.details.DetailsViewModel
import com.example.model.CocktailDataUI

@Composable
fun DetailsRoute(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState: DetailsScreenState by viewModel.screenState.collectAsState()
    CocktailDetailsScreen(uiState = uiState, onRefresh = viewModel::update)
}

@Composable
private fun CocktailDetailsScreen(
    uiState: DetailsScreenState,
    onRefresh: () -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()
    val lazyListState: LazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {

        Content(
            isLoading = uiState.isLoading,
            isNetworkError = uiState.networkError,
            data = uiState.data,
        )

        /**
         * PullToRefresh в M3 не работает без LazyColumn*/
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize(),
        ) {}

        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        if (pullRefreshState.isRefreshing) {
            LaunchedEffect(key1 = true) {
                onRefresh()
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun WithData() {
    CocktailDetailsScreen(
        uiState = DetailsScreenState(
            isLoading = false,
            networkError = false,
            data = CocktailDataUI.mock(),
        ),
        onRefresh = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingWithData() {
    CocktailDetailsScreen(
        uiState = DetailsScreenState(
            isLoading = true,
            networkError = false,
            data = CocktailDataUI.mock(),
        ),
        onRefresh = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun NetworkError() {
    CocktailDetailsScreen(
        uiState = DetailsScreenState(
            isLoading = true,
            networkError = true,
            data = CocktailDataUI.mock(),
        ),
        onRefresh = {}
    )
}