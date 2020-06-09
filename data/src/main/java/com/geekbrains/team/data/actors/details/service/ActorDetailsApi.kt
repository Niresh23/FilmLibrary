package com.geekbrains.team.data.actors.details.service

import com.geekbrains.team.data.actors.details.service.model.ActorDetailsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorDetailsApi {
    @GET("/3/person/{person_id}?")
    fun getActorDetails(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<ActorDetailsResponse>
}