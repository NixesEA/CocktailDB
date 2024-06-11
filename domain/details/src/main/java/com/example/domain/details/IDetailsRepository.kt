package com.example.domain.details

import com.example.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface IDetailsRepository {
    fun getCocktailDetailsByID(id:Int): Flow<Result<Cocktail>>
}

