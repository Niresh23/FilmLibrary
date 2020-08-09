package com.geekbrains.team.domain.tv.searchTV.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.domain.tv.searchTV.repository.SearchTVRepository
import com.geekbrains.team.domain.tv.commonRepository.TVGenresRepository
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.model.fillTVGenres
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import javax.inject.Inject

class GetSearchedTV @Inject constructor(
    private val repositoryTV: SearchTVRepository,
    private val repositoryTVGenres: TVGenresRepository,
    private val favoriteSeriesRepository: FavoriteSeriesRepository
) :
    UseCase<List<TVShow>, GetSearchedTV.Params> {
    override fun execute(params: Params): Single<List<TVShow>> =
        Single.zip(
            repositoryTVGenres.fetch(),
            repositoryTV.fetch(
                query = params.query,
                page = params.page
            ),
            favoriteSeriesRepository.getFavoriteSeriesIds(),
            Function3 { tvGenres, tvShows, ids ->
                fillTVGenres(tvGenres, tvShows)
                tvShows.map { it.like = ids.contains(it.id) }
                tvShows
            }
        )

    data class Params(
        val query: String,
        val page: Int? = null
    ): Param()
}