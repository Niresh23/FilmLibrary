package com.geekbrains.team.domain.movies.movieDetails.interactor

import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.toMovieActor
import com.geekbrains.team.domain.movies.commonRepository.MovieCreditsRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.movieDetails.repository.MovieDetailsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMovieActorsUseCase @Inject constructor(
    private val movieActorsRepository: MovieCreditsRepository
): UseCase<List<Movie.Actor>?, GetMovieActorsUseCase.Params> {

    data class Params(val id: Int)

    override fun execute(params: Params): Single<out List<Movie.Actor>?> {
        return movieActorsRepository.fetch(params.id).map { it.cast?.map { castPerson ->
                castPerson.toMovieActor()
            }
        }
    }
}