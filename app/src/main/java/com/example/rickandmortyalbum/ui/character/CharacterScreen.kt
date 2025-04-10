package com.example.rickandmortyalbum.ui.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmortyalbum.data.model.DataResponse
import com.example.rickandmortyalbum.R
import com.example.rickandmortyalbum.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    characterId: Int,
    onBackClick: () -> Unit,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getCharacter(characterId)
    }

    val itemState = viewModel.data.collectAsState()

    when (itemState.value.status) {        
        DataResponse.Status.SUCCESS -> {
            itemState.value.data?.let { CharacterDetailWidget(it) }
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(onClick = { onBackClick() }) {
                    Text(stringResource(R.string.back_to_previous))
                }
            }
        }
        DataResponse.Status.ERROR -> {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Error: ${itemState.value.message}", color = Color.Red)
                Button(onClick = { onBackClick() }) {
                    Text(stringResource(R.string.back_to_previous))
                }
            }
        }
        DataResponse.Status.LOADING -> {
            LoadingView()
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlue),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material.CircularProgressIndicator()
    }
}

@Preview
@Composable
fun PreviewCharacterDetailScreen() {
    val viewModel: CharacterViewModel =  hiltViewModel()
    CharacterScreen(
        characterId = 0,
        onBackClick = {},
        viewModel = viewModel
    )
}