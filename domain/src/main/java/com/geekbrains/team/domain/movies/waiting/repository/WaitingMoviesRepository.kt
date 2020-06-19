package com.geekbrains.team.domain.movies.waiting.repository

import io.reactivex.Completable
import io.reactivex.Single

interface WaitingMoviesRepository {
    fun getWaitingMoviesIds(): Single<List<Int>>
    fun addWaitingMovieId(id: Int): Completable
    fun deleteFromWaiting(id: Int): Completable
}