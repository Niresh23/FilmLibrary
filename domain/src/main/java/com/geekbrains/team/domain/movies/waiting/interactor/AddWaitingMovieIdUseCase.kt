package com.geekbrains.team.domain.movies.waiting.interactor

import com.geekbrains.team.domain.base.UseCaseCompletable
import com.geekbrains.team.domain.movies.waiting.repository.WaitingMoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddWaitingMovieIdUseCase @Inject constructor(
    private val repository: WaitingMoviesRepository
) : UseCaseCompletable<AddWaitingMovieIdUseCase.Params> {

    data class Params(
        val id: Int
    )

    override fun execute(params: Params): Completable =
        repository.addWaitingMovieId(params.id)

}