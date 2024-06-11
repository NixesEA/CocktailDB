package com.example.api.mapper

import com.example.api.model.CocktailDataNetwork
import com.example.api.model.CocktailResponse
import com.example.model.Cocktail

internal fun CocktailResponse.mapToDomain(): List<Cocktail> {
    return this.drinks?.map { it.mapToDomain() }?: emptyList()
}

internal fun CocktailDataNetwork.mapToDomain(): Cocktail {
    return Cocktail(
        idDrink = this.idDrink,
        strDrink = this.strDrink,
        strCategory = this.strCategory,
        strAlcoholic = this.strAlcoholic,
        strDrinkThumb = this.strDrinkThumb,
        strInstructions = this.strInstructions,
        strIngredient1 = this.strIngredient1,
        strIngredient2 = this.strIngredient2,
        strIngredient3 = this.strIngredient3,
        strIngredient4 = this.strIngredient4,
        strIngredient5 = this.strIngredient5,
        strIngredient6 = this.strIngredient6,
        strIngredient7 = this.strIngredient7,
        strIngredient8 = this.strIngredient8,
        strIngredient9 = this.strIngredient9,
        strIngredient10 = this.strIngredient10,
        strIngredient11 = this.strIngredient11,
        strIngredient12 = this.strIngredient12,
        strIngredient13 = this.strIngredient13,
        strIngredient14 = this.strIngredient14,
        strIngredient15 = this.strIngredient15,
    )
}