package com.example.finalapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalapplication.ui.screens.FavoritesScreen
import com.example.finalapplication.ui.screens.HeroListScreen
import com.example.finalapplication.ui.screens.HeroDetailScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HeroListScreen(navController = navController)
        }
        composable("favorites") {
            FavoritesScreen(navController = navController)
        }
        composable("heroDetail/{heroId}") { backStackEntry ->
            val heroId = backStackEntry.arguments?.getString("heroId")?.toIntOrNull()
            if (heroId != null) {
                HeroDetailScreen(heroId = heroId)
            }
        }
    }
}
