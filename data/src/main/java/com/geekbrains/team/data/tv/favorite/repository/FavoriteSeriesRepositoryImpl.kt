package com.geekbrains.team.data.tv.favorite.repository

import com.geekbrains.team.data.tv.favorite.dao.FavoriteSeriesDao
import com.geekbrains.team.data.tv.favorite.model.FavoriteSeriesEntity
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import io.reactivex.Single
import javax.inject.Inject

class FavoriteSeriesRepositoryImpl @Inject constructor(private val dao: FavoriteSeriesDao)
    : FavoriteSeriesRepository {

    override fun getFavoriteSeriesIds(): Single<List<Int>> = dao.getAllIds()

    override fun addFavoriteSeriesId(id: Int) = dao.insert(FavoriteSeriesEntity(id))

    override fun deleteSeriesFromFavorite(id: Int) = dao.delete(id)

}