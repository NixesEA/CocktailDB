package com.example.api.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun provideApi(): CocktailAPI {
        return CocktailsService.getApi()
    }
}