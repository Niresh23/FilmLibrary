package com.geekbrains.team.domain.tv.nowPlayingTV.interactor

import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.movies.commonRepository.VideosRepository
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.nowPlayingTV.repository.NowPlayingTVRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class GetFirstNowPlayingTV @Inject constructor(
    private val repository: NowPlayingTVRepository,
    @param:Named("TVVideos") private val tvVideoRepository: VideosRepository,
    private val favoriteSeriesRepository: FavoriteSeriesRepository
) :
    UseCase<TVShow, None> {
    override fun execute(params: None): Single<TVShow> =
        repository.fetch()
            .flatMap { tv -> Single.just(tv.first()) }
            .flatMap { tv ->
                tvVideoRepository.fetch(tv.id).map {
                    tv.videos = it
                    tv
                }
                favoriteSeriesRepository.getFavoriteSeriesIds().map {
                    tv.like = it.contains(tv.id)
                    tv
                }
            }
}