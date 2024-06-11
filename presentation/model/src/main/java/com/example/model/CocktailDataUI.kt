package com.example.model

data class CocktailDataUI(
    val idDrink: String,
    val name: String,
    val imageUrl: String,
    val category: String,
    val alcoholic: String,
    val instructions: String,
    val ingredients: List<String> = emptyList()
) {
    companion object {
        fun mock() = CocktailDataUI(
            idDrink = "123321",
            name = "Mock name",
            imageUrl = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
            category = "category",
            alcoholic = "mock alcoholic",
            instructions = "long instruction",
            ingredients = listOf("Vodka","Mint","Cats anusay")
        )
    }
}