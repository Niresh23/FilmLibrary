package com.geekbrains.team.domain.tv.waiting.interactor

import com.geekbrains.team.domain.base.UseCaseCompletable
import com.geekbrains.team.domain.tv.waiting.repository.WaitingSeriesRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddWaitingSeriesIdUseCase @Inject constructor(
    private val repository: WaitingSeriesRepository
) : UseCaseCompletable<AddWaitingSeriesIdUseCase.Params>{

    data class Params(
        val id: Int
    )

    override fun execute(params: Params): Completable =
        repository.addWaitingSeriesId(params.id)
}