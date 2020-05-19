package com.geekbrains.team.domain.actors.details.repository

import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import io.reactivex.Single

interface ActorMovieCreditsRepository {
    fun fetch(id: Int): Single<ActorCreditsInfo>
}