package com.example.cache

import com.example.sharedcache.ISelectedCocktailCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    @Singleton
    fun provideDetailsRepository(): ISelectedCocktailCache {
        return SelectedCocktailRepository()
    }
}