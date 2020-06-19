package com.geekbrains.team.data.tv.waiting.repository

import com.geekbrains.team.data.tv.waiting.dao.WaitingSeriesDao
import com.geekbrains.team.data.tv.waiting.model.WaitingSeriesEntity
import com.geekbrains.team.domain.tv.waiting.repository.WaitingSeriesRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WaitingSeriesRepositoryImpl @Inject constructor(
    private val dao: WaitingSeriesDao
): WaitingSeriesRepository {
    override fun getWaitingSeriesIds(): Single<List<Int>> =
        dao.getAllIds()


    override fun addWaitingSeriesId(id: Int): Completable =
        dao.insert(WaitingSeriesEntity(id))

    override fun deleteFromWaiting(id: Int): Completable =
        dao.delete(id)
}