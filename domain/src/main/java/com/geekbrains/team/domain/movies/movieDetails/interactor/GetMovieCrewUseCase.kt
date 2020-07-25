package com.geekbrains.team.domain.movies.movieDetails.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.toMovieMember
import com.geekbrains.team.domain.movies.commonRepository.MovieCreditsRepository
import com.geekbrains.team.domain.movies.model.Movie
import io.reactivex.Single
import javax.inject.Inject

class GetMovieCrewUseCase @Inject constructor(
    private val movieCreditsRepository: MovieCreditsRepository
): UseCase<List<Movie.Member>?, GetMovieCrewUseCase.Params> {
    data class Params(val id: Int)

    override fun execute(params: Params): Single<out List<Movie.Member>?> =
        movieCreditsRepository.fetch(params.id).map { it.crew?.map { crewPerson ->
                crewPerson.toMovieMember()
            }
        }
}