package com.geekbrains.team.domain.tv.favorite.interactor

import com.geekbrains.team.domain.base.UseCaseCompletable
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddFavoriteSeriesUseCase @Inject constructor(
    private val repository: FavoriteSeriesRepository
) : UseCaseCompletable<AddFavoriteSeriesUseCase.Params> {

    data class Params(val id: Int)

    override fun execute(params: Params): Completable =
        repository.addFavoriteSeriesId(id = params.id)

}