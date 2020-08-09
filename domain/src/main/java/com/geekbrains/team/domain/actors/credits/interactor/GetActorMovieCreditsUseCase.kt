package com.geekbrains.team.domain.actors.credits.interactor

import com.geekbrains.team.domain.actors.credits.repository.ActorMovieCreditsRepository
import com.geekbrains.team.domain.actors.credits.repository.ActorTVCreditsRepository
import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.Param
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.function.DoubleFunction
import javax.inject.Inject

class GetActorMovieCreditsUseCase @Inject constructor(
    private val getActorMovieCreditsRepository: ActorMovieCreditsRepository
): UseCase<ActorCreditsInfo, GetActorMovieCreditsUseCase.Params> {

    data class Params(
        val id: Int
    ): Param()

    override fun execute(params: Params): Single<out ActorCreditsInfo> =
        getActorMovieCreditsRepository.fetch(params.id)
}