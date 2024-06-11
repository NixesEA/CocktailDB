package com.example.api.repository

import com.example.api.api.CocktailAPI
import com.example.api.mapper.mapToDomain
import com.example.api.model.CocktailResponse
import com.example.domain.details.IDetailsRepository
import com.example.model.Cocktail
import com.example.random.IRandomRepository
import com.example.search.ISearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val api: CocktailAPI) :
    IDetailsRepository,
    IRandomRepository,
    ISearchRepository {
    override fun getCocktailDetailsByID(id: Int): Flow<Result<Cocktail>> =
        flow {
            val result: Result<CocktailResponse> = api.getCocktailDetailsByID(id)
            emit(result.map { it.mapToDomain().first() })
        }.flowOn(Dispatchers.IO)

    override fun getRandomCocktail(): Flow<Result<Cocktail>> =
        flow {
            val result = api.getRandomCocktail()
            emit(result.map { it.mapToDomain().first() })
        }.flowOn(Dispatchers.IO)

    override fun search(request: String): Flow<Result<List<Cocktail>>> =
        combine(
            searchByName(request),
            searchByIngredient(request),
        ) { names, ing ->
            if (names.isSuccess || ing.isSuccess) {
                Result.success(
                    listOf(
                        *names.getOrNull()?.toTypedArray() ?: emptyArray(),
                        *ing.getOrNull()?.toTypedArray() ?: emptyArray()
                    )
                )
            } else if (names.isFailure) {
                names
            } else {
                ing
            }
        }.flowOn(Dispatchers.IO)

    private fun searchByName(cocktailName: String): Flow<Result<List<Cocktail>>> =
        flow {
            val result = api.searchByName(cocktailName = cocktailName)
            emit(result.map {
                it.drinks
                    ?.map { it.mapToDomain() }
                    ?: emptyList()
            })
        }.flowOn(Dispatchers.IO)

    private fun searchByIngredient(ingredientName: String): Flow<Result<List<Cocktail>>> =
        flow {
            val result = api.searchByIngredient(ingredientName = ingredientName)
            emit(result.map {
                it.ingredient
                    ?.map { it.mapToDomain() }
                    ?: emptyList()
            })
        }.flowOn(Dispatchers.IO)
}
