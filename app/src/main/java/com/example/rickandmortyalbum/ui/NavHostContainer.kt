package com.example.rickandmortyalbum.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickandmortyalbum.ui.character.CharacterScreen
import com.example.rickandmortyalbum.ui.characters.CharactersScreen

@Composable
fun NavHostContainer(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "charactersScreen",
    ) {
        // First screen
        composable("charactersScreen") {
            CharactersScreen(
                onItemClick = { characterId ->
                    navController.navigate("characterScreen/$characterId")
                }
            )
        }

        // Second screen
        composable("characterScreen/{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->
            val passedId = backStackEntry.arguments?.getInt("characterId") ?: 0

            CharacterScreen(
                characterId = passedId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}