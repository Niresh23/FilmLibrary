package com.geekbrains.team.domain.actors.credits.interactor

import com.geekbrains.team.domain.actors.credits.repository.ActorTVCreditsRepository
import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.Param
import io.reactivex.Single
import javax.inject.Inject

class GetActorTVCreditsUseCase @Inject constructor(
    private val getActorTVCreditsRepository: ActorTVCreditsRepository
): UseCase<ActorCreditsInfo,GetActorTVCreditsUseCase.Params> {
    data class Params(val id: Int): Param()

    override fun execute(params: Params): Single<out ActorCreditsInfo> =
        getActorTVCreditsRepository.fetch(params.id)
}