package com.geekbrains.team.domain.movies.favoriteMovies.interactor

import com.geekbrains.team.domain.base.UseCaseCompletable
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteFromFavoriteMoviesUseCase @Inject constructor(private val repository: FavoriteMoviesRepository):
    UseCaseCompletable<DeleteFromFavoriteMoviesUseCase.Params> {

    override fun execute(params: Params): Completable {
        return repository.deleteMovieFromFavorite(params.id)
    }

    data class Params(
        val id: Int
    )

}