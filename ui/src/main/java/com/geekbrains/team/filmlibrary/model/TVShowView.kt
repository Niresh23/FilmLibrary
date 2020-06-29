package com.geekbrains.team.filmlibrary.model

import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.filmlibrary.Const

data class TVShowView(
    val id: Int,
    val originalName: String, // Оригинальное название (строка)
    val name: String, // Название (строка)
    val numberOfSeasons: String, // Количество сезонов (строка)
    val voteAverage: String,  // Рейтинг imdb
    val lastAirDate: String, // Дата выхода (строка, для будущих)
    val images: List<String>, // Массив больших фото (строки, url)
    val trailer: String, // Ссылка на трейлер (строка, url)
    val posterPath: String, // Маленькое фото (постер, строка, url)
    val backdropPath: String,
    val genres: String, // Жанры (строки)
    val firstAirDate: String, // Год выхода первого сезона (строка, для уже вышедших. можно объединить, по аналогии с фильмами, если там объединяем)
    val originCountry: String, // Страны производства (строка)
    val episodeRunTime: String,
    val overview: String, // Описание
    val productionCompanies: String, // Площадка, на которой показывался (строка)
    var director:String? = null,
    var writer: String? = null,
    var producer: String? = null,
    var like: Boolean = false
) {
    data class Actor(
        val id: Int,
        val name: String,
        val posterPath: String?,
        val character: String
    )
}

fun TVShow.toTVShowView() =
    TVShowView(
        id = id,
        originalName = originalName,
        backdropPath = backdropPath,
        name = name,
        numberOfSeasons = numberOfSeasons.toString(),
        voteAverage = voteAverage.toString(),
        trailer = trailer(),
        // TODO Fix this to feature.
        images = images?.posters?.map { it.url } ?: listOf(),
        firstAirDate = firstAirDate,
        episodeRunTime = episodeRunTime?.joinToString() ?: "",
        lastAirDate = lastAirDate ?: "",
        genres = genres.joinToString(),
        overview = overview,
        originCountry = originCountry?.joinToString { it } ?: "",
        posterPath = posterPath,
        productionCompanies = productionCompanies?.joinToString { it.name } ?: "",
        director = director,
        writer = writer,
        producer = producer,
        like = like
    )

private fun TVShow.trailer(): String = videos?.firstOrNull {
    it.site.equals(Const.YOUTUBE, true) && it.type.equals(
        Const.TRAILER,
        true
    )
}?.let {
    Const.YOUTUBE_SITE + it.key
} ?: ""