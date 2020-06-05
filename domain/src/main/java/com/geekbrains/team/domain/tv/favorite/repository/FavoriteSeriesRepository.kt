package com.geekbrains.team.domain.tv.favorite.repository

import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteSeriesRepository {
    fun getFavoriteSeriesIds(): Single<List<Int>>
    fun addFavoriteSeriesId(id: Int): Completable
    fun deleteSeriesFromFavorite(id: Int): Completable
}