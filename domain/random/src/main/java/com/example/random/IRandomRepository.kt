package com.example.random

import com.example.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface IRandomRepository {
    fun getRandomCocktail(): Flow<Result<Cocktail>>
}