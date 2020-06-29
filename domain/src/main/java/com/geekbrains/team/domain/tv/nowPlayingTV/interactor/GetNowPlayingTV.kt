package com.geekbrains.team.domain.tv.nowPlayingTV.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.nowPlayingTV.repository.NowPlayingTVRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetNowPlayingTV @Inject constructor(
    private val repository: NowPlayingTVRepository,
    private val favoriteSeriesRepository: FavoriteSeriesRepository) :
    UseCase<List<TVShow>, GetNowPlayingTV.Params> {
    override fun execute(params: Params): Single<List<TVShow>> =
        Single.zip(
            repository.fetch(page = params.page),
            favoriteSeriesRepository.getFavoriteSeriesIds(),
            BiFunction { tvShows, ids ->
                tvShows.map { it.like = ids.contains(it.id) }
                tvShows
            }
        )

    data class Params(val page: Int)
}