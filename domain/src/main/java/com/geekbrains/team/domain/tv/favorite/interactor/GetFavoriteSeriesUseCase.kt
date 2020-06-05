package com.geekbrains.team.domain.tv.favorite.interactor

import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.tv.details.rerpository.DetailsTVRepository
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.model.TVShow
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetFavoriteSeriesUseCase @Inject constructor(
    private val favoriteSeriesRepository: FavoriteSeriesRepository,
    private val seriesDetailsRepository: DetailsTVRepository
): UseCase<List<TVShow>, None> {
    override fun execute(params: None): Single<out List<TVShow>> =
        favoriteSeriesRepository.getFavoriteSeriesIds()
            .flatMapObservable { list ->
                Observable.fromIterable(list)
            }.flatMapSingle { item ->
                seriesDetailsRepository.fetch(item)
            }.toList()
}