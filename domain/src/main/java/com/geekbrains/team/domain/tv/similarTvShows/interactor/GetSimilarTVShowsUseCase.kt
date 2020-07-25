package com.geekbrains.team.domain.tv.similarTvShows.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.UseCaseAbs
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.similarTvShows.repository.SimilarTVShowsRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSimilarTVShowsUseCase @Inject constructor(
    private val repository: SimilarTVShowsRepository,
    private val favoriteSeriesRepository: FavoriteSeriesRepository
): UseCaseAbs(), UseCase<List<TVShow>, GetSimilarTVShowsUseCase.Params> {
    data class Params(
        val id: Int,
        val page: Int
    )

    override fun execute(params: Params): Single<out List<TVShow>> = Single.zip(
        repository.fetch(id = params.id, page = params.page),
        favoriteSeriesRepository.getFavoriteSeriesIds(),
        BiFunction { tvShows, ids ->
            tvShows.map { it.like = ids.contains(it.id) }
            tvShows
        }
    )

}