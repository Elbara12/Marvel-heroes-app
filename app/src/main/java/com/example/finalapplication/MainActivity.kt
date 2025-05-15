// MainActivity.kt
package com.example.finalapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalapplication.ui.navigation.NavGraph
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val backStackEntry = navController.currentBackStackEntryAsState()

            MaterialTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == "home",
                                onClick = { navController.navigate("home") },
                                label = { Text("heroes") },
                                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) }
                            )
                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == "favorites",
                                onClick = { navController.navigate("favorites") },
                                label = { Text("Favorites") },
                                icon = { Icon(Icons.Default.Star, contentDescription = null) }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }

    }
}
