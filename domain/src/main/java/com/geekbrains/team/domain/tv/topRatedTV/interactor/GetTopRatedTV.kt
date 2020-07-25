package com.geekbrains.team.domain.tv.topRatedTV.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.UseCaseAbs
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.topRatedTV.repository.TopRatedTVRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetTopRatedTV @Inject constructor(
    private val repository: TopRatedTVRepository,
    private val favoriteSeriesRepository: FavoriteSeriesRepository) : UseCaseAbs(),
    UseCase<List<TVShow>, GetTopRatedTV.Params> {
    override fun execute(params: Params): Single<MutableList<TVShow>> =
        Single.zip(
            repository.fetch(params.page),
            favoriteSeriesRepository.getFavoriteSeriesIds(),
            BiFunction { tvShows, ids ->
                tvShows.map { it.like = ids.contains(it.id) }
                tvShows
            }
        )

    data class Params(val page: Int)
}