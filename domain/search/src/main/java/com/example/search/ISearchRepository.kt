package com.example.search

import com.example.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun search(request: String): Flow<Result<List<Cocktail>>>
}