package com.geekbrains.team.domain.movies.favoriteMovies.interactor

import com.geekbrains.team.domain.base.UseCaseCompletable
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddFavoriteMovieIdUseCase @Inject constructor(
    private val repository: FavoriteMoviesRepository
) : UseCaseCompletable<AddFavoriteMovieIdUseCase.Params> {

    override fun execute(params: Params): Completable =
        repository.addFavoriteMovieId(id = params.id)

    data class Params(val id: Int): Param()
}