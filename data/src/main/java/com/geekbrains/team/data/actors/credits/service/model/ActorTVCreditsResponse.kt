package com.geekbrains.team.data.actors.credits.service.model

import com.geekbrains.team.data.Const
import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import com.google.gson.annotations.SerializedName

data class ActorTVCreditsResponse(
    val id: Int,
    val cast: List<MovieInfo>,
    val crew: List<JobInfo>
) {
    data class MovieInfo(
        val id: Int,
        val character: String,
        val name: String,


        @SerializedName("poster_path")
        val posterPath: String?
    )

    data class JobInfo(
        val id: Int,
        val department: String,
        val job: String,
        val name: String,

        @SerializedName("poster_path")
        val posterPath: String?
    )
}

fun ActorTVCreditsResponse.toActorCreditsInfo() = ActorCreditsInfo(
    id = this.id,
    cast = this.cast.map{ ActorCreditsInfo.MovieInfo(
        id = it.id,
        title = it.name,
        character = it.character,
        posterPath =  it.posterPath?.let {poster -> Const.IMAGE_PREFIX + poster } ?: ""
    ) },
    crew = this.crew.map { ActorCreditsInfo.JobInfo (
        id = it.id,
        title = it.name,
        job = it.job,
        posterPath = it.posterPath?.let {poster -> Const.IMAGE_PREFIX + poster } ?: ""
    ) }
)