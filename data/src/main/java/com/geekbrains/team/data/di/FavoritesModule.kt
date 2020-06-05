package com.geekbrains.team.data.di

import com.geekbrains.team.data.movies.favoriteMovies.repository.FavoriteMoviesRepositoryImpl
import com.geekbrains.team.data.tv.favorite.repository.FavoriteSeriesRepositoryImpl
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class FavoritesModule {

    @Binds
    @Singleton
    abstract fun provideRepositoryFavoriteMovies(repository: FavoriteMoviesRepositoryImpl): FavoriteMoviesRepository

    @Binds
    @Singleton
    abstract fun provideFavoriteSeriesRepository(repository: FavoriteSeriesRepositoryImpl): FavoriteSeriesRepository
}