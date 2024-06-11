@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.example.search.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.core.Loader
import com.example.core.NoInternetScreen
import com.example.model.CocktailDataUI
import com.example.search.R
import com.example.search.SearchScreenAction
import com.example.search.SearchScreenEffect
import com.example.search.SearchScreenState
import com.example.search.SearchViewModel

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    navToDetails: () -> Unit
) {
    val screenEffect by viewModel.screenEffect.collectAsState(initial = SearchScreenEffect.None)
    LaunchedEffect(key1 = screenEffect) {
        when (screenEffect) {
            SearchScreenEffect.NavToDetails -> navToDetails()
            SearchScreenEffect.None -> {}
        }
    }

    val screenState by viewModel.screenState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        if (screenState.cocktailsList.isEmpty()) {
            focusRequester.requestFocus()
        }
    }

    val modifier = Modifier
        .fillMaxSize()
//        .pullRefresh(pullRefreshState)
//        .verticalScroll(rememberScrollState())

    val onRequestChanged: (String) -> Unit = remember {
        { newRequest -> viewModel.onAction(SearchScreenAction.RequestChanged(newRequest)) }
    }
    val onDetailsAction: (Int) -> Unit = remember {
        { selectedID -> viewModel.onAction(SearchScreenAction.NavigateToDetails(selectedID)) }
    }
    val onRefreshAction: () -> Unit = remember {
        { viewModel.onAction(SearchScreenAction.Update) }
    }

    SearchScreenContent(
        uiState = screenState,
        focusRequester = focusRequester,
        modifier = modifier,
        onDetailsAction = onDetailsAction,
        onValueChange = onRequestChanged,
        onRefresh = onRefreshAction,
    )
}

@Composable
private fun SearchScreenContent(
    uiState: SearchScreenState,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    onDetailsAction: (Int) -> Unit,
    onValueChange: (String) -> Unit,
    onRefresh: () -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        Loader(isVisible = uiState.isLoading)

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                value = uiState.searchRequest,
                onValueChange = onValueChange,
                placeholder = { Text(text = stringResource(R.string.search_field_placeholder)) }
            )

            if (uiState.isNetworkError && uiState.cocktailsList.isEmpty()) {
                NoInternetScreen(modifier = modifier)
            } else {
                SearchingResult(data = uiState.cocktailsList, onDetailsAction = onDetailsAction)
            }
        }

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

@Composable
private fun SearchingResult(data: List<CocktailDataUI>, onDetailsAction: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = data, key = { it.idDrink }) {
            SearchingItem(
                data = it,
                onDetailsAction = onDetailsAction,
                modifier = Modifier.animateItemPlacement(),
            )
        }
    }
}

@Composable
private fun SearchingItem(
    data: CocktailDataUI,
    onDetailsAction: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onDetailsAction(data.idDrink.toInt())
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .height(50.dp)
                .aspectRatio(1f / 1f)
                .clip(CircleShape)
                .background(color = Color.LightGray, shape = CircleShape),
            model = data.imageUrl,
            contentDescription = "${data.alcoholic} ${data.name}"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = data.name)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = data.alcoholic)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchingItemPreview() {
    SearchingItem(data = CocktailDataUI.mock(), onDetailsAction = {})
}

@Preview(showSystemUi = true)
@Composable
private fun SearchPreview() {
    SearchScreenContent(
        uiState = SearchScreenState(
            isLoading = true,
            isNetworkError = false,
            searchRequest = "some request",
            cocktailsList = listOf(CocktailDataUI.mock())
        ),
        focusRequester = FocusRequester(),
        onDetailsAction = {},
        onValueChange = {},
        onRefresh = {},
    )
    SearchRoute(navToDetails = {})
}

@Preview(showSystemUi = true)
@Composable
private fun NetworkError() {
    SearchScreenContent(
        uiState = SearchScreenState(
            isLoading = false,
            isNetworkError = true,
            searchRequest = "",
            cocktailsList = emptyList()
        ),
        focusRequester = FocusRequester(),
        onDetailsAction = {},
        onValueChange = {},
        onRefresh = {},
    )
    SearchRoute(navToDetails = {})
}