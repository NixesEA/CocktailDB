package com.example.api.repository

import com.example.api.api.CocktailAPI
import com.example.domain.details.IDetailsRepository
import com.example.random.IRandomRepository
import com.example.search.ISearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideDetailsRepository(
        repository: CocktailRepository
    ): IDetailsRepository {
        return repository
    }

    @Provides
    fun provideRandomRepository(
        repository: CocktailRepository
    ): IRandomRepository {
        return repository
    }

    @Provides
    fun provideSearchRepository(
        repository: CocktailRepository
    ): ISearchRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: CocktailAPI
    ): CocktailRepository {
        return CocktailRepository(api)
    }
}