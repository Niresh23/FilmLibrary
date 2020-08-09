package com.geekbrains.team.domain.tv.details.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.Credits
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.domain.base.model.toTVShowActor
import com.geekbrains.team.domain.base.model.toTVShowMember
import com.geekbrains.team.domain.movies.commonRepository.VideosRepository
import com.geekbrains.team.domain.tv.commonRepository.TVGenresRepository
import com.geekbrains.team.domain.tv.commonRepository.TVImagesRepository
import com.geekbrains.team.domain.tv.credits.repository.TVCreditsRepository
import com.geekbrains.team.domain.tv.details.rerpository.DetailsTVRepository
import com.geekbrains.team.domain.tv.favorite.repository.FavoriteSeriesRepository
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.model.fillTVGenres
import io.reactivex.Single
import io.reactivex.functions.Function5
import io.reactivex.functions.Function6
import javax.inject.Inject
import javax.inject.Named

class GetTVDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsTVRepository,
    private val creditsRepository: TVCreditsRepository,
    private val genres: TVGenresRepository,
    private val images: TVImagesRepository,
    @param: Named( "TVVideos") private val videos: VideosRepository,
    private val favoriteSeriesRepository: FavoriteSeriesRepository
    ): UseCase<TVShow, GetTVDetailsUseCase.Params>{

    data class Params(val id: Int): Param()

    companion object {
        const val DIRECTOR = "DIRECTOR"
        const val WRITING = "WRITING"
        const val PRODUCER = "PRODUCER"
        const val ELEMENTS_TO_TAKE = 3
    }

    override fun execute(params: Params): Single<TVShow> {
        return Single.zip(
            detailsRepository.fetch(params.id),
            creditsRepository.fetch(params.id),
            genres.fetch(),
            images.fetch(params.id),
            videos.fetch(params.id),
            favoriteSeriesRepository.getFavoriteSeriesIds(),
            Function6  {
                sourceTVShow, credits, listGenres, images, videos, ids ->
                fillTVGenres(listGenres, sourceTVShow)
                fillTVShowCredits(credits, sourceTVShow)
                sourceTVShow.images = images
                sourceTVShow.videos = videos
                sourceTVShow.like = ids.contains(sourceTVShow.id)

                sourceTVShow

            })
    }

    private fun fillTVShowCredits(credits: Credits, tvShow: TVShow) {
        tvShow.apply {
            cast = credits.cast?.map { it.toTVShowActor() }
            crew = credits.crew?.map { it.toTVShowMember() }
            director = personByJob(credits, DIRECTOR)
            producer = personByJob(credits, PRODUCER)
            writer = personByDepartment(credits, WRITING)
        }
    }
}

private fun personByJob(credits: Credits, filter: String): String?  =
    credits.crew?.filter { person -> person.job.equals(filter, true) }
        ?.take(GetTVDetailsUseCase.ELEMENTS_TO_TAKE)
        ?.joinToString { it.name }

private fun personByDepartment(credits: Credits, filter: String): String? =
    credits.crew?.filter { person -> person.department.equals(filter, true) }
        ?.take(GetTVDetailsUseCase.ELEMENTS_TO_TAKE)
        ?.joinToString { it.name }

