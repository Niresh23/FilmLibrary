package com.geekbrains.team.domain.tv.favorite.interactor

import com.geekbrains.team.domain.base.UseCaseCompletable
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteSeriesFromFavoriteUseCase @Inject constructor(
    private val repository: FavoriteSeriesRepository
) : UseCaseCompletable<DeleteSeriesFromFavoriteUseCase.Params> {

    data class Params(
        val id: Int
    ): Param()

    override fun execute(params: Params): Completable {
        return repository.deleteSeriesFromFavorite(params.id)
    }
}