package com.example.domain.details

import com.example.model.Cocktail
import com.example.sharedcache.ISelectedCocktailCache
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsUseCase @Inject constructor(
    private val repository: IDetailsRepository,
    private val cache: ISelectedCocktailCache,
) {
     fun getDetailsById(id: Int): Flow<Result<Cocktail>> {
        return repository.getCocktailDetailsByID(id)
    }

    fun getSelectItem(): Int {
        return cache.getSelectedCocktailId()
    }
}