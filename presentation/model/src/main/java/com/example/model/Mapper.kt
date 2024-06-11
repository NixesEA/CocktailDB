package com.example.model

fun Cocktail.mapDomainToUi() = CocktailDataUI(
    idDrink = this.idDrink,
    name = this.strDrink,
    category = this.strCategory,
    alcoholic = this.strAlcoholic,
    imageUrl = this.strDrinkThumb,
    instructions = this.strInstructions,
    ingredients = listOfNotNull(
        this.strIngredient1,
        this.strIngredient2,
        this.strIngredient3,
        this.strIngredient4,
        this.strIngredient5,
        this.strIngredient6,
        this.strIngredient7,
        this.strIngredient8,
        this.strIngredient9,
        this.strIngredient10,
        this.strIngredient11,
        this.strIngredient12,
        this.strIngredient13,
        this.strIngredient14,
        this.strIngredient15,
    )
)

fun List<Cocktail>.mapDomainToUi(): List<CocktailDataUI> = this.map { it.mapDomainToUi() }