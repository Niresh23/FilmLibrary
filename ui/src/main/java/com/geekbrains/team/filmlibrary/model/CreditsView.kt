package com.geekbrains.team.filmlibrary.model

import com.geekbrains.team.domain.actors.model.ActorCreditsInfo

data class CreditsView(
    val cast: List<CastCredit>?,
    val crew: List<CrewCredit>?
) {
    data class CastCredit(
        val id: Int,
        val title: String,
        val character: String,
        val posterPath: String?
    )

    data class CrewCredit(
        val id: Int,
        val title: String,
        val job: String,
        val posterPath: String?
    )
}

fun ActorCreditsInfo.toCreditsView(): CreditsView =
    CreditsView(
        cast = this.cast.map { CreditsView.CastCredit(
            id = it.id,
            character = it.character,
            posterPath = it.posterPath,
            title = it.title
        )},
        crew = this.crew.map { CreditsView.CrewCredit(
            id = it.id,
            job = it.job,
            posterPath = it.posterPath,
            title = it.title
        ) }
    )

fun CreditsView.CastCredit.toPersonView(): PersonView =
    PersonView(
        id = this.id,
        character = character,
        name = title,
        posterPath = this.posterPath ?: ""
    )

fun CreditsView.CrewCredit.toPersonView(): PersonView =
    PersonView(
        id = this.id,
        character = this.job,
        posterPath = this.posterPath ?: "",
        name = title
    )