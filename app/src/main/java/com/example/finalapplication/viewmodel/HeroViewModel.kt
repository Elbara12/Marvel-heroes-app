package com.example.finalapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.finalapplication.data.model.Hero
import com.example.finalapplication.data.remote.RetrofitInstance
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class HeroViewModel : ViewModel() {
    var heroList by mutableStateOf<List<Hero>>(emptyList())
    var isLoading by mutableStateOf(true)

    init {
        loadHeroes()
    }

    private fun loadHeroes() {
        viewModelScope.launch {
            try {
                heroList = RetrofitInstance.api.getAllHeroes()
            } catch (e: Exception) {
                heroList = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    fun reloadHeroes() {
        viewModelScope.launch {
            isLoading = true
            try {
                heroList = RetrofitInstance.api.getAllHeroes()
            } catch (e: Exception) {
                heroList = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}
