package com.example.api.api

import com.example.api.model.CocktailResponse
import com.example.api.model.IngredientResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailAPI {
    @GET("random.php")
    suspend fun getRandomCocktail(): Result<CocktailResponse>

    @GET("lookup.php")
    suspend fun getCocktailDetailsByID(
        @Query("i") id: Int
    ): Result<CocktailResponse>

    @GET("search.php")
    suspend fun searchByName(
        @Query("s") cocktailName: String? = null,
    ): Result<CocktailResponse>

    @GET("search.php")
    suspend fun searchByIngredient(
        @Query("i") ingredientName: String? = null,
    ): Result<IngredientResponse>
}