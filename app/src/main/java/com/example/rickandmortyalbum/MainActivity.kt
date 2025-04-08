package com.example.rickandmortyalbum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyalbum.ui.NavHostContainer
import com.example.rickandmortyalbum.ui.theme.RickAndMortyAlbumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyAlbumTheme {
                // Navigation controller
                val navController = rememberNavController()

                // Navigation settings
                NavHostContainer(navController)
            }
        }
    }
}