package com.example.rickandmortyalbum.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortyalbum.ui.characters.widgets.ErrorView
import com.example.rickandmortyalbum.ui.characters.widgets.LoadingView
import com.example.rickandmortyalbum.ui.characters.widgets.NotLoadView
import com.example.rickandmortyalbum.ui.characters.widgets.CharacterItemView
import com.example.rickandmortyalbum.ui.characters.widgets.SearchBarWidget
import com.example.rickandmortyalbum.ui.characters.widgets.ToggleBarWidget
import com.example.rickandmortyalbum.ui.theme.RickAndMortyAlbumTheme
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharactersScreen(
    onItemClick: (Int) -> Unit,
    viewModel: CharactersViewModel = hiltViewModel()
) {

    val characters = viewModel.charactersStateFlow.collectAsLazyPagingItems()
    val searchQuery = viewModel.searchStateFlow.collectAsState()
    val useDbState = viewModel.useDb.collectAsState(false)
    val isRefreshing = characters.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { characters.refresh() }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchAllCharacters(searchQuery.value)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xff66b2ff))
                .padding(innerPadding)
        ) {
            ToggleBarWidget(
                modifier = Modifier.padding(8.dp),
                useDb = useDbState.value,
                onValueChanged = { useDb -> viewModel.onUseDbChanged(useDb) }
            )

            SearchBarWidget(
                query = searchQuery.value,
                onQueryChange = { newQuery -> viewModel.onSearchQueryChanged(newQuery) },
                onSearch = {
                    Timber.d("Searching for: ${searchQuery.value}")
                    viewModel.onSearchClicked()
                },
                modifier = Modifier
                    .padding(8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Rick and Morty Characters",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Filtered by Name: ${searchQuery.value}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 110.dp)
                ) {
                    items(
                        count = characters.itemCount
                    ) { index ->
                        characters[index]?.let {
                            CharacterItemView(
                                character = it,
                                onItemClick = { onItemClick(it.id) }
                            )
                        }
                    }

                    characters.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingView() }
                            }
                            loadState.append is LoadState.Loading -> {
                                item { LoadingView() }
                            }

                            loadState.append is LoadState.NotLoading -> {
                                item { NotLoadView() }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val error = (loadState.refresh as LoadState.Error).error
                                item { ErrorView(error.message ?: "Unknown Error") { retry() } }
                            }
                            loadState.append is LoadState.Error -> {
                                val error = (loadState.append as LoadState.Error).error
                                item { ErrorView(error.message ?: "Unknown Error") { retry() } }
                            }
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
    RickAndMortyAlbumTheme {
        CharactersScreen(onItemClick = {})
    }
}