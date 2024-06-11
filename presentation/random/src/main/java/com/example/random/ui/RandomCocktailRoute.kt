@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.random.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
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
import com.example.model.CocktailDataUI
import com.example.random.RandomScreenState
import com.example.random.RandomViewModel

@Composable
fun RandomCocktailRoute(
    viewModel: RandomViewModel = hiltViewModel()
) {
    val uiState: RandomScreenState by viewModel.screenState.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    CocktailDetailsScreen(uiState, pullRefreshState, viewModel::fetchData)
}

@Composable
fun CocktailDetailsScreen(
    uiState: RandomScreenState,
    pullRefreshState: PullToRefreshState,
    onRefresh: () -> Unit,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    val modifier = Modifier
        .fillMaxSize()

    Box(
        modifier = Modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        Content(
            modifier = modifier,
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

        LaunchedEffect(key1 = uiState.isLoading) {
            if (uiState.isLoading) {
                pullRefreshState.startRefresh()
            } else {
                pullRefreshState.endRefresh()
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DataAndLoadingPreview() {
    CocktailDetailsScreen(
        uiState = RandomScreenState(
            isLoading = true,
            networkError = false,
            data = CocktailDataUI.mock(),
        ),
        pullRefreshState = rememberPullToRefreshState { true },
        onRefresh = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun BlankAndLoadingPreview() {
    CocktailDetailsScreen(
        uiState = RandomScreenState(
            isLoading = true,
            networkError = false,
            data = null,
        ),
        pullRefreshState = rememberPullToRefreshState { true },
        onRefresh = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun NetworkErrorPreview() {
    CocktailDetailsScreen(
        uiState = RandomScreenState(
            isLoading = false,
            networkError = true,
            data = CocktailDataUI.mock(),
        ),
        pullRefreshState = rememberPullToRefreshState { true },
        onRefresh = {}
    )
}
