package com.geekbrains.team.data.tv.favorite.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekbrains.team.data.tv.favorite.model.FavoriteSeriesEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteSeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: FavoriteSeriesEntity): Completable

    @Query("DELETE FROM FavoriteSeriesEntity WHERE id = :seriesId")
    fun delete(seriesId: Int): Completable

    @Query("SELECT id FROM FavoriteSeriesEntity")
    fun getAllIds(): Single<List<Int>>
}