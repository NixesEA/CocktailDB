package com.example.search

import com.example.model.Cocktail
import com.example.sharedcache.ISelectedCocktailCache
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: ISearchRepository,
    private val cache: ISelectedCocktailCache,
) {
    fun search(request: String): Flow<Result<List<Cocktail>>> {
        return repository.search(request)
    }

    fun setSelectItem(id: Int) {
        return cache.setSelectedCocktailId(id)
    }
}