package com.example.rickandmortyalbum.ui.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.data.model.DataResponse
import com.example.rickandmortyalbum.ui.characters.CharactersViewModel
import com.example.rickandmortyalbum.ui.theme.RickAndMortyAlbumTheme

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
            itemState.value.data?.let { CharacterSuccessView(it) }
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(onClick = { onBackClick() }) {
                    Text("Back to previous screen")
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
                    Text("Back to previous screen")
                }
            }
        }
        DataResponse.Status.LOADING -> {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun PreviewCharacterDetailScreen() {
    val viewModel: CharacterViewModel =  hiltViewModel()
    CharacterScreen(
        characterId = 0,
        onBackClick = {  },
        viewModel = TODO()
    )
}