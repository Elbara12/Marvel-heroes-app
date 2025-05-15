package com.example.finalapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalapplication.viewmodel.HeroViewModel
import com.example.finalapplication.data.model.Hero
import com.example.finalapplication.ui.components.HeroImageWithLoader
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeroListScreen(
    navController: NavHostController,
    viewModel: HeroViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val allHeroes = viewModel.heroList
    val filteredHeroes = allHeroes.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    val isLoading = viewModel.isLoading
    val isRefreshing = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing.value,
            onRefresh = {
                isRefreshing.value = true
                coroutineScope.launch {
                    viewModel.reloadHeroes()
                    isRefreshing.value = false
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Research hero...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredHeroes) { hero ->
                        HeroItem(hero = hero, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun HeroItem(hero: Hero, navController: NavHostController) {
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
