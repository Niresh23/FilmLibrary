package com.geekbrains.team.data.tv.waiting.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geekbrains.team.data.tv.waiting.model.WaitingSeriesEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WaitingSeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(series: WaitingSeriesEntity): Completable

    @Query("DELETE FROM WaitingSeriesEntity WHERE id = :seriesId")
    fun delete(seriesId: Int): Completable

    @Query("SELECT id FROM WaitingSeriesEntity")
    fun getAllIds(): Single<List<Int>>
}