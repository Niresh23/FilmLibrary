package com.geekbrains.team.domain.movies.upcomingMovies.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.UseCaseAbs
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.upcomingMovies.repository.UpcomingMoviesRepository
import com.geekbrains.team.domain.movies.waiting.repository.WaitingMoviesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val repository: UpcomingMoviesRepository,
    private val waitingMoviesRepository: WaitingMoviesRepository) : UseCaseAbs(),
    UseCase<List<Movie>, GetUpcomingMovies.Params> {
    override fun execute(params: Params): Single<List<Movie>> = Single.zip(
        repository.fetch(page = params.page), waitingMoviesRepository.getWaitingMoviesIds(),
        BiFunction { movies, ids ->
            movies.map { it.waiting = ids.contains(it.id) }
            movies
        }
    )

    data class Params(val page: Int): Param()
}