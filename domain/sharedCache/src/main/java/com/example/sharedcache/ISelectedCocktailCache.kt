package com.example.sharedcache

interface ISelectedCocktailCache {
    fun setSelectedCocktailId(id:Int)
    fun getSelectedCocktailId():Int
}