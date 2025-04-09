package com.example.rickandmortyalbum.ui.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmortyalbum.R
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailWidget(
    character: CharacterItem
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.3f)
            ),
            title = {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "${stringResource(R.string.status)} ${character.status}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Cyan
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "${stringResource(R.string.gender)} ${character.gender}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "${stringResource(R.string.location)} ${character.location.name}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}