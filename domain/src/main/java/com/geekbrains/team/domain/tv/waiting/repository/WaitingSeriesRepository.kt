package com.geekbrains.team.domain.tv.waiting.repository

import io.reactivex.Completable
import io.reactivex.Single

interface WaitingSeriesRepository {
    fun getWaitingSeriesIds(): Single<List<Int>>
    fun addWaitingSeriesId(id: Int): Completable
    fun deleteFromWaiting(id: Int): Completable
}