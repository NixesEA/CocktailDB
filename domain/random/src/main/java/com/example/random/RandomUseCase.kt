package com.example.random

import com.example.model.Cocktail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RandomUseCase @Inject constructor(
    private val repository: IRandomRepository,
) {
     fun getRandomDetails(): Flow<Result<Cocktail>> {
        return repository.getRandomCocktail()
    }
}