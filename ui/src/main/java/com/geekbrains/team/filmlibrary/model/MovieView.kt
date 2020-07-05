package com.geekbrains.team.filmlibrary.model

import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.filmlibrary.Const.TRAILER
import com.geekbrains.team.filmlibrary.Const.YOUTUBE
import com.geekbrains.team.filmlibrary.Const.YOUTUBE_SITE
import com.geekbrains.team.filmlibrary.parseToShortFormat

data class MovieView(
    val id: Int,
    val title: String, // Название (строка)
    val originalTitle: String, // Оригинальное название (строка)
    val voteAverage: String, // Рейтинг imdb (строка)
    val releaseDate: String, // Дата выхода (строка, для будущих)
    val images: List<String>, // Массив больших фото (строки, url)
    val trailer: String, // Ссылка на трейлер (строка, url)
    val posterPath: String, // Маленькое фото (постер, строка, url)
    val genres: String, // Жанры (строки)
    val backdropPath: String, // Картинка из фильма
    val productionCountries: String, // Страны производства (строка)
    val runtime: String, // Продолжительность (строка)
    val overview: String, // Описание (строка)
    val producer: String,
    val writer: String,
    val director: String,
    val releaseYear:String,
    var like: Boolean = false,
    var waiting: Boolean = false
)

fun Movie.toMovieView() = MovieView(
    id = id,
    title = title,
    originalTitle = originalTitle,
    voteAverage = voteAverage.toString(),
    releaseDate = releaseDate.parseToShortFormat(),
    images = movieImages(),
    trailer = movieTrailer(),
    posterPath = posterPath,
    genres = genres.joinToString(),
    backdropPath = backdropPath,
    productionCountries = productionCountries?.joinToString { it.name } ?: "",
    runtime = runtime.toString(),
    overview = overview,
    producer = producer ?: "",
    writer = writer ?: "",
    director = director ?: "",
    releaseYear = releaseYear,
    like = this.like,
    waiting = this.waiting
)

private fun Movie.movieImages(): List<String> = images?.let {
    it.backdrops.map { backdrop ->
        backdrop.url
    }
} ?: listOf()

private fun Movie.movieTrailer(): String = videos?.firstOrNull {
    it.site.equals(YOUTUBE, true) && it.type.equals(
        TRAILER,
        true
    )
}?.let {
    YOUTUBE_SITE + it.key
} ?: ""