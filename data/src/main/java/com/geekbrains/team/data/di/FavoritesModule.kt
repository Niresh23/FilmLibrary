package com.geekbrains.team.data.di

import com.geekbrains.team.data.movies.favoriteMovies.repository.FavoriteMoviesRepositoryImpl
import com.geekbrains.team.data.movies.waiting.repository.WaitingMoviesRepositoryImpl
import com.geekbrains.team.data.tv.favorite.repository.FavoriteSeriesRepositoryImpl
import com.geekbrains.team.data.tv.waiting.repository.WaitingSeriesRepositoryImpl
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.movies.waiting.repository.WaitingMoviesRepository
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.waiting.repository.WaitingSeriesRepository
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

    @Binds
    @Singleton
    abstract fun provideWaitingMoviesRepository(repository: WaitingMoviesRepositoryImpl): WaitingMoviesRepository

    @Binds
    @Singleton
    abstract fun provideWaitingSeriesRepository(repository: WaitingSeriesRepositoryImpl): WaitingSeriesRepository
}