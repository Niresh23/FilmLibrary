package com.geekbrains.team.filmlibrary.model
import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.tv.model.TVShow

data class PersonView(
    val id: Int,
    val name: String,
    val character: String,
    val posterPath: String
)

fun Movie.Actor.toPersonView(): PersonView = PersonView(
    id = id,
    name = name,
    character = character,
    posterPath = posterPath ?: ""
)

fun Movie.Member.toPersonView(): PersonView = PersonView(
    id = id,
    name = name,
    character = job,
    posterPath = posterPath ?: ""
)

fun TVShow.Actor.toPersonView(): PersonView = PersonView(
    id = id,
    name = name,
    character = character,
    posterPath = posterPath ?: ""
)

fun TVShow.Member.toPersonView(): PersonView = PersonView(
    id = id,
    name = name,
    character = job,
    posterPath = posterPath ?: ""
)

fun ActorCreditsInfo.MovieInfo.toPersonView(): PersonView = PersonView(
    id = this.id,
    name = this.title,
    character = this.character,
    posterPath = this.posterPath ?: ""
)

fun ActorCreditsInfo.JobInfo.toPersonView(): PersonView = PersonView(
    id = this.id,
    name = this.title,
    character = this.job,
    posterPath = this.posterPath ?: ""
)