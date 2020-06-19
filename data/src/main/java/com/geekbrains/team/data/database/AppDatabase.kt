package com.geekbrains.team.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekbrains.team.data.movies.favoriteMovies.dao.FavoriteMoviesDao
import com.geekbrains.team.data.movies.favoriteMovies.model.FavoriteMovieEntity
import com.geekbrains.team.data.movies.waiting.dao.WaitingMoviesDao
import com.geekbrains.team.data.movies.waiting.model.WaitingMovieEntity
import com.geekbrains.team.data.tv.favorite.dao.FavoriteSeriesDao
import com.geekbrains.team.data.tv.favorite.model.FavoriteSeriesEntity
import com.geekbrains.team.data.tv.waiting.dao.WaitingSeriesDao
import com.geekbrains.team.data.tv.waiting.model.WaitingSeriesEntity

@Database(entities = [FavoriteMovieEntity::class, FavoriteSeriesEntity::class, WaitingMovieEntity::class,
WaitingSeriesEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
    abstract fun favoriteSeriesDao(): FavoriteSeriesDao
    abstract fun waitingMoviesDao(): WaitingMoviesDao
    abstract fun waitingSeriesDao(): WaitingSeriesDao
}