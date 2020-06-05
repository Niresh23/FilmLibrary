package com.geekbrains.team.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekbrains.team.data.movies.favoriteMovies.dao.FavoriteMoviesDao
import com.geekbrains.team.data.movies.favoriteMovies.model.FavoriteMovieEntity
import com.geekbrains.team.data.tv.favorite.dao.FavoriteSeriesDao
import com.geekbrains.team.data.tv.favorite.model.FavoriteSeriesEntity

@Database(entities = [FavoriteMovieEntity::class, FavoriteSeriesEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
    abstract fun favoriteSeriesDao(): FavoriteSeriesDao
}