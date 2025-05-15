package com.example.finalapplication.data.remote

import com.example.finalapplication.data.model.Hero
import retrofit2.http.GET

interface ApiService {
    @GET("all.json")
    suspend fun getAllHeroes(): List<Hero>
}
