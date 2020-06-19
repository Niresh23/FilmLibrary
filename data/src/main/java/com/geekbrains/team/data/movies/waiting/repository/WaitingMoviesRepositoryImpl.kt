package com.geekbrains.team.data.movies.waiting.repository

import com.geekbrains.team.data.movies.waiting.dao.WaitingMoviesDao
import com.geekbrains.team.data.movies.waiting.model.WaitingMovieEntity
import com.geekbrains.team.domain.movies.waiting.repository.WaitingMoviesRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WaitingMoviesRepositoryImpl @Inject constructor(
    private val dao: WaitingMoviesDao
): WaitingMoviesRepository {
    override fun getWaitingMoviesIds(): Single<List<Int>> =
        dao.getAllMoviesIds()

    override fun addWaitingMovieId(id: Int): Completable =
        dao.insert(WaitingMovieEntity(id))

    override fun deleteFromWaiting(id: Int): Completable =
        dao.delete(id)
}