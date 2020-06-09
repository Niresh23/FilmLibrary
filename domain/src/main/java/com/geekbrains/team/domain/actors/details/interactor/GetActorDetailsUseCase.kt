package com.geekbrains.team.domain.actors.details.interactor

import android.util.Log
import com.geekbrains.team.domain.actors.details.repository.ActorDetailsRepository
import com.geekbrains.team.domain.actors.credits.repository.ActorMovieCreditsRepository
import com.geekbrains.team.domain.actors.credits.repository.ActorTVCreditsRepository
import com.geekbrains.team.domain.actors.model.ActorInformation
import com.geekbrains.team.domain.base.UseCase
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val actorDetailsRepository: ActorDetailsRepository,
    private val actorMovieCreditsRepository: ActorMovieCreditsRepository,
    private val actorTVCreditsRepository: ActorTVCreditsRepository
):
    UseCase<ActorInformation, GetActorDetailsUseCase.Params> {

    override fun execute(params: Params): Single<out ActorInformation> {
        Log.e("GetActorDetailsUseCase", "execute()")
        return actorDetailsRepository.fetch(params.id)
    }

    data class Params(
        val id: Int
    )
}
//
//return Single.zip(
//actorDetailsRepository.fetch(params.id),
//actorMovieCreditsRepository.fetch(params.id),
//actorTVCreditsRepository.fetch(params.id),
//Function3 { details, movieCredits, tvCredits ->
//    details.movieCredits = movieCredits
//    details.tvCredits = tvCredits
//    Log.e("GetActorDetailsUseCase", details.name)
//    details
//}
//)