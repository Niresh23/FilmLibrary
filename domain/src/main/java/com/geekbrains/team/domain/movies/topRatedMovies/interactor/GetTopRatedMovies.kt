package com.geekbrains.team.domain.movies.topRatedMovies.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.UseCaseAbs
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.topRatedMovies.repository.TopRatedMoviesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val repository: TopRatedMoviesRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository) : UseCaseAbs(),
    UseCase<MutableList<Movie>, GetTopRatedMovies.Params> {
    override fun execute(params: Params): Single<MutableList<Movie>> =
        Single.zip(repository.fetch(params.page),
                    favoriteMoviesRepository.getFavoriteMoviesIds(),
        BiFunction { movies, ids ->
            movies.map { it.like = ids.contains(it.id) }
            movies
        })


    data class Params(val page: Int)
}