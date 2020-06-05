package com.geekbrains.team.data.di

import android.content.Context
import androidx.room.Room
import com.geekbrains.team.data.database.AppDatabase
import com.geekbrains.team.data.movies.favoriteMovies.dao.FavoriteMoviesDao
import com.geekbrains.team.data.tv.favorite.dao.FavoriteSeriesDao
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DatabaseModule{

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "FavoriteMovies.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideFavoriteMoviesDao(database: AppDatabase): FavoriteMoviesDao {
        return database.favoriteMoviesDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteSeriesDao(database: AppDatabase): FavoriteSeriesDao {
        return database.favoriteSeriesDao()
    }
}