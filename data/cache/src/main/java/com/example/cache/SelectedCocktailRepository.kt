package com.example.cache

import com.example.sharedcache.ISelectedCocktailCache

internal class SelectedCocktailRepository : ISelectedCocktailCache {

    private var savedId = 0

    override fun setSelectedCocktailId(id: Int) {
        savedId = id
    }

    override fun getSelectedCocktailId(): Int {
        return savedId
    }
}

