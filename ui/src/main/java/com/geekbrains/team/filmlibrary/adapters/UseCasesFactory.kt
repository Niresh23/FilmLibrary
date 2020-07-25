package com.geekbrains.team.filmlibrary.adapters

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.movies.movieDetails.interactor.GetMovieActorsUseCase
import com.geekbrains.team.domain.movies.movieDetails.interactor.GetMovieCrewUseCase
import com.geekbrains.team.domain.movies.nowPlayingMovies.interactor.GetNowPlayingMovies
import com.geekbrains.team.domain.movies.similarMovie.interactor.GetSimilarMoviesUseCase
import com.geekbrains.team.domain.movies.upcomingMovies.interactor.GetUpcomingMovies
import javax.inject.Inject

class UseCasesFactory @Inject constructor(
    private val similarMoviesUseCase: GetSimilarMoviesUseCase,
    private val nowPlayingMoviesUseCase: GetNowPlayingMovies,
    private val upcomingMoviesUseCase: GetUpcomingMovies,
    private val movieActorsUseCase: GetMovieActorsUseCase,
    private val movieCrewUseCase: GetMovieCrewUseCase
) {
    fun getUseCase(enum: UseCasesEnum) {
        when(enum) {
            UseCasesEnum.MOVIE_ACTORS -> return
        }
    }
}