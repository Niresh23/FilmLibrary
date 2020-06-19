package com.geekbrains.team.domain.tv.waiting.interactor

import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.tv.details.rerpository.DetailsTVRepository
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.waiting.repository.WaitingSeriesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetWaitingSeriesUseCase @Inject constructor(
    private val waitingSeriesRepository: WaitingSeriesRepository,
    private val seriesDetailsRepository: DetailsTVRepository
) : UseCase<List<TVShow>, None> {

    override fun execute(params: None): Single<out List<TVShow>> =
        waitingSeriesRepository.getWaitingSeriesIds()
            .flatMapObservable { list ->
                Observable.fromIterable(list)
            }.flatMapSingle { item ->
                seriesDetailsRepository.fetch(item)
            }.toList()
}