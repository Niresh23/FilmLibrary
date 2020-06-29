package com.geekbrains.team.domain.tv.model

import com.geekbrains.team.domain.base.model.Genre
import com.geekbrains.team.domain.base.model.Images
import com.geekbrains.team.domain.base.model.MovieAndTVShow
import com.geekbrains.team.domain.base.model.Video

data class TVShow(
    val backdropPath: String,
    val createdBy: List<CreatedBy>? = null,
    val episodeRunTime: List<Int>? = null,
    val firstAirDate: String,
    val genreIds: List<Int>? = null,
    val genres: MutableList<String> = ArrayList(),
    val homepage: String = "",
    val id: Int,
    val trailer: String = "",
    val inProduction: Boolean? = null,
    val languages: List<String>? = null,
    val lastAirDate: String? = null,
    val lastEpisodeToAir: LastEpisodeToAir? = null,
    val name: String,
    val networks: List<Network>? = null,
    val nextEpisodeToAir: Any? = null,
    val numberOfEpisodes: Int? = null,
    val numberOfSeasons: Int? = null,
    val originCountry: List<String>? = null,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Int,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>? = null,
    val seasons: List<Season>? = null,
    val status: String? = null,
    val type: String? = null,
    var videos: List<Video>? = null,
    val voteAverage: Int,
    val voteCount: Int,
    var cast: List<Actor>? = null, // Актеры
    var crew: List<Member>? =null, //Съемочная группа
    var images: Images? = null,
    var director: String? = null,
    var writer: String? = null,
    var producer: String? = null,
    var like: Boolean = false
) : MovieAndTVShow() {
    data class CreatedBy(
        val creditId: String,
        val gender: Int,
        val id: Int,
        val name: String,
        val profilePath: String
    )

    data class Genre(
        val id: Int,
        val name: String
    )

    data class LastEpisodeToAir(
        val airDate: String,
        val episodeNumber: Int,
        val id: Int,
        val name: String,
        val overview: String,
        val productionCode: String,
        val seasonNumber: Int,
        val showId: Int,
        val stillPath: String,
        val voteAverage: Double,
        val voteCount: Int
    )

    data class Network(
        val id: Int,
        val logoPath: String,
        val name: String,
        val originCountry: String
    )

    data class ProductionCompany(
        val id: Int,
        val logoPath: String?,
        val name: String,
        val originCountry: String
    )

    data class Season(
        val airDate: String,
        val episodeCount: Int,
        val id: Int,
        val name: String,
        val overview: String,
        val posterPath: String,
        val seasonNumber: Int
    )

    data class Actor(
        val id: Int,
        val name: String,
        val posterPath: String?,
        val character: String
    )

    data class Member (
        val id: Int,
        val name: String,
        val job: String,
        val posterPath: String?
    )
}

fun fillTVGenres(
    tvGenres: List<Genre>,
    tv: List<TVShow>
): List<TVShow> =
    tv.apply {
        val genresTVMap = tvGenres.map { it.id to it.name }.toMap()

        map { tvShow ->
            tvShow.genreIds?.map {
                val result = genresTVMap[it]
                result?.let { tvShow.genres.add(result) }
            }
        }
    }

fun fillTVGenres(
    tvGenres: List<Genre>,
    tvShow: TVShow
): TVShow =
    tvShow.apply {
        val genresTVMap = tvGenres.map { it.id to it.name }.toMap()
        tvShow.genreIds?.map {
            val result = genresTVMap[it]
            result?.let { tvShow.genres.add(result) }
        }

    }