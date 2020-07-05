package com.geekbrains.team.domain.movies.similarMovie.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.similarMovie.repository.SimilarMoviesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val repository: SimilarMoviesRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository
): UseCase<List<Movie>, GetSimilarMoviesUseCase.Param>
{

    override fun execute(params: Param): Single<out List<Movie>> =
        Single.zip(repository.fetch(id = params.id, page = params.page),
        favoriteMoviesRepository.getFavoriteMoviesIds(), BiFunction{
                movies, ids ->
                movies.map { it.like = ids.contains(it.id) }
                movies
            })

    data class Param(val id: Int, val page: Int?): com.geekbrains.team.domain.base.model.Param()
}