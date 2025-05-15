package com.example.finalapplication.ui.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.finalapplication.data.model.Hero
import com.example.finalapplication.viewmodel.FavoritesViewModel
import com.example.finalapplication.ui.components.HeroImageWithLoader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = object : ViewModelProvider.AndroidViewModelFactory(
            context.applicationContext as Application
        ) {}
    )
    val favorites by favoritesViewModel.favorites.collectAsState()
    val filteredFavorites = favorites
        .filter { it.name.contains(searchQuery, ignoreCase = true) }
        .sortedBy { it.name }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My favorites") })
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No heroes in favorites")
            }
        } else {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {

                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Research favorite...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredFavorites) { hero ->
                        FavoriteItem(hero = hero, navController = navController)
                    }
                }
            }
        }

    }
}

@Composable
fun FavoriteItem(hero: Hero, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("heroDetail/${hero.id}")
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            HeroImageWithLoader(url = hero.images.sm)

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = hero.name, style = MaterialTheme.typography.titleMedium)
                val fullName = hero.biography.fullName.ifBlank { "Not available" }

                Text(
                    text = "Full Name: $fullName",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
