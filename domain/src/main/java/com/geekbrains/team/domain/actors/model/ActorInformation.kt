package com.geekbrains.team.domain.actors.model

data class ActorInformation(
    val id: Int,
    val name: String,
    val birthday: String?,
    val deathDay: String?,
    val gender: Int,
    val biography: String,
    val popularity: Double,
    val profilePath: String?,
    val alsoKnownAs: List<String>,
    val placeOfBirth: String?,
    val imdbId: String,
    val homepage: String?,
    val knownFor: String
)
