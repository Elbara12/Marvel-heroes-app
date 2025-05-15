package com.example.finalapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalapplication.data.model.Hero
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val favoritesCollection = db.collection("favorites")

    private val _favorites = MutableStateFlow<List<Hero>>(emptyList())
    val favorites: StateFlow<List<Hero>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    fun addFavorite(hero: Hero) {
        viewModelScope.launch {
            favoritesCollection.document(hero.id.toString()).set(hero)
        }
    }

    fun removeFavorite(hero: Hero) {
        viewModelScope.launch {
            favoritesCollection.document(hero.id.toString()).delete()
        }
    }

    private fun loadFavorites() {
        favoritesCollection.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener
            val heroes = snapshot.toObjects(Hero::class.java)
            _favorites.value = heroes
        }
    }
}
