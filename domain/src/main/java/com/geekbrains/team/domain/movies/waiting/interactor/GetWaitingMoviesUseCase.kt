package com.geekbrains.team.domain.movies.waiting.interactor

import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.movieDetails.repository.MovieDetailsRepository
import com.geekbrains.team.domain.movies.waiting.repository.WaitingMoviesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetWaitingMoviesUseCase @Inject constructor(
    private val waitingMoviesRepository: WaitingMoviesRepository,
    private val movieDetailsRepository: MovieDetailsRepository
) : UseCase<List<Movie>, None> {


    override fun execute(params: None): Single<out List<Movie>> =
        waitingMoviesRepository.getWaitingMoviesIds()
            .flatMapObservable { list ->
                Observable.fromIterable(list)
            }.flatMapSingle { item ->
                movieDetailsRepository.fetch(item)
            }.toList()
}