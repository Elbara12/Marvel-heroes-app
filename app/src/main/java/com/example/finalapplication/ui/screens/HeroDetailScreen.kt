package com.example.finalapplication.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.finalapplication.ui.components.HeroImageWithLoader
import com.example.finalapplication.viewmodel.HeroViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalapplication.viewmodel.FavoritesViewModel
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HeroDetailScreen(
    heroId: Int,
    viewModel: HeroViewModel = viewModel()
) {
    val hero = viewModel.heroList.find { it.id == heroId }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = object : ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application) {}
    )
    val favorites by favoritesViewModel.favorites.collectAsState()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(favorites) {
        isFavorite = favorites.any { it.id == heroId }
    }

    if (hero == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Hero not found.")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(text = hero.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            HeroImageWithLoader(
                url = hero.images.lg,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text("Full Name:", fontWeight = FontWeight.Bold)
            Text(hero.biography.fullName.ifBlank { "Not available" })

            Spacer(Modifier.height(8.dp))
            Text("Publisher:", fontWeight = FontWeight.Bold)
            Text(hero.biography.publisher.ifBlank { "Not available" })

            Text("Place of Birth:", fontWeight = FontWeight.Bold)
            Text(hero.biography.placeOfBirth.ifBlank { "Not available" })

            Text("First Appearance:", fontWeight = FontWeight.Bold)
            Text(hero.biography.firstAppearance.ifBlank { "Not available" })

            Text("Alignment:", fontWeight = FontWeight.Bold)
            Text(hero.biography.alignment.ifBlank { "Not available" })

            Spacer(Modifier.height(16.dp))
            Text("Power Stats:", fontWeight = FontWeight.Bold)
            Text("🧠 Intelligence: ${hero.powerstats.intelligence.takeIf { it > 0 } ?: "Not available"}")
            Text("💪 Strength: ${hero.powerstats.strength.takeIf { it > 0 } ?: "Not available"}")
            Text("⚡ Speed: ${hero.powerstats.speed.takeIf { it > 0 } ?: "Not available"}")
            Text("🛡️ Durability: ${hero.powerstats.durability.takeIf { it > 0 } ?: "Not available"}")
            Text("✨ Power: ${hero.powerstats.power.takeIf { it > 0 } ?: "Not available"}")
            Text("🥋 Combat: ${hero.powerstats.combat.takeIf { it > 0 } ?: "Not available"}")

            Spacer(Modifier.height(16.dp))
            Text("Appearance:", fontWeight = FontWeight.Bold)
            Text("Gender: ${hero.appearance.gender.ifBlank { "Not available" }}")
            Text("Race: ${hero.appearance.race ?: "Not available"}")
            Text("Height: ${hero.appearance.height.takeIf { it.isNotEmpty() }?.joinToString() ?: "Not available"}")
            Text("Weight: ${hero.appearance.weight.takeIf { it.isNotEmpty() }?.joinToString() ?: "Not available"}")
            Text("Eye Color: ${hero.appearance.eyeColor.ifBlank { "Not available" }}")
            Text("Hair Color: ${hero.appearance.hairColor.ifBlank { "Not available" }}")

            Spacer(Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    scope.launch {
                        if (isFavorite) {
                            favoritesViewModel.removeFavorite(hero)
                        } else {
                            favoritesViewModel.addFavorite(hero)
                        }
                        isFavorite = !isFavorite
                    }
                }) {
                    Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
                }

                Button(onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Hero: ${hero.name}\nFull name: ${hero.biography.fullName}\nImage: ${hero.images.lg}")
                    }
                    startActivity(context, Intent.createChooser(shareIntent, "Share with..."), null)
                }) {
                    Text("Share")
                }
            }
        }
    }
    }