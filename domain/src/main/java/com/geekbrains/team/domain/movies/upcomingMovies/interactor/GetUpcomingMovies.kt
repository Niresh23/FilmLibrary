package com.geekbrains.team.domain.movies.upcomingMovies.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.upcomingMovies.repository.UpcomingMoviesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val repository: UpcomingMoviesRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository) :
    UseCase<List<Movie>, GetUpcomingMovies.Params> {
    override fun execute(params: Params): Single<List<Movie>> = Single.zip(
        repository.fetch(page = params.page), favoriteMoviesRepository.getFavoriteMoviesIds(),
        BiFunction { movies, ids ->
            movies.map { it.like = ids.contains(it.id) }
            movies
        }
    )

    data class Params(val page: Int)
}