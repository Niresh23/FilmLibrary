package com.geekbrains.team.domain.movies.model

import com.geekbrains.team.domain.base.model.Genre
import com.geekbrains.team.domain.base.model.Images
import com.geekbrains.team.domain.base.model.MovieAndTVShow
import com.geekbrains.team.domain.base.model.Video
import java.util.*
import kotlin.collections.ArrayList

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: Boolean? = null,
    val budget: Int? = 0,
    val genreIds: List<Int>? = null,
    val genres: MutableList<String> = ArrayList(),
    val id: Int,
    var videos: List<Video>? = null,
    var images: Images? = null,
    val homepage: String? = null,
    val imdbId: String? = "",
    var trailer: String = "",
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Int,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>? = null,
    val productionCountries: List<ProductionCountry>? = null,
    val releaseDate: Date,
    val releaseYear: String,
    val revenue: Int = 0,
    val runtime: Int = 0,
    var cast: List<Actor>? = null, // Актеры
    var crew: List<Member>? = null, // Команда
    val spokenLanguages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String,
    val video: Boolean,
    val voteAverage: Int,
    val voteCount: Int,
    var producer: String? = null,
    var writer: String? = null,
    var director: String? = null,
    var like: Boolean = false,
    var waiting: Boolean = false
) : MovieAndTVShow() {

    data class Actor(
        val id: Int,
        val name: String,
        val posterPath: String?,
        val character: String
    )

    data class Member(
        val id: Int,
        val name: String,
        val job: String,
        val posterPath: String?
    )

    data class ProductionCompany(
        val id: Int,
        val logoPath: Any?,
        val name: String,
        val originCountry: String
    )

    data class ProductionCountry(
        val iso31661: String,
        val name: String
    )

    data class SpokenLanguage(
        val iso6391: String,
        val name: String
    )
}

fun fillMovieGenres(
    moviesGenres: List<Genre>,
    movie: Movie
) {
    val genresMoviesMap = moviesGenres.map { it.id to it.name }.toMap()

    movie.genreIds?.map {
        val result = genresMoviesMap[it]
        result?.let { movie.genres.add(result) }
    }
}