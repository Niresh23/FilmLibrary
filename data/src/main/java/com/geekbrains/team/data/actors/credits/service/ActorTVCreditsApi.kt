package com.geekbrains.team.data.actors.credits.service

import com.geekbrains.team.data.actors.credits.service.model.ActorTVCreditsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorTVCreditsApi {
    @GET("/3/person/{person_id}/tv_credits?")
    fun getActorTVCredits(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<ActorTVCreditsResponse>
}