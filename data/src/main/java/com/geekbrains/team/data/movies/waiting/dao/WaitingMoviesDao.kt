package com.geekbrains.team.data.movies.waiting.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekbrains.team.data.movies.waiting.model.WaitingMovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WaitingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: WaitingMovieEntity): Completable

    @Query("DELETE FROM WaitingMovieEntity WHERE id = :movieId")
    fun delete(movieId: Int): Completable

    @Query("SELECT id FROM WaitingMovieEntity")
    fun getAllMoviesIds(): Single<List<Int>>
}