package com.geekbrains.team.data.actors.credits.service.model

import com.google.gson.annotations.SerializedName

data class ActorMovieCreditsResponse(
    val id: Int,
    val cast: List<MovieInfo>,
    val crew: List<JobInfo>
) {
    data class MovieInfo(
        val id: Int,
        val character: String,

        @SerializedName("backdrop_path")
        val backdropPath: String?,

        @SerializedName("poster_path")
        val posterPath: String

    )

    data class JobInfo(
        val id: Int,
        val department: String,
        val job: String,
        @SerializedName("backdrop_path")
        val backdropPath: String?,

        @SerializedName("poster_path")
        val posterPath: String
    )
}