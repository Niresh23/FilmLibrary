package com.geekbrains.team.domain.movies.nowPlayingMovies.interactor

import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.domain.movies.commonRepository.MoviesGenresRepository
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.model.fillMovieGenres
import com.geekbrains.team.domain.movies.nowPlayingMovies.repository.NowPlayingMoviesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import javax.inject.Inject

class GetNowPlayingMovies @Inject constructor(
    private val nowPlayingMoviesRepository: NowPlayingMoviesRepository,
    private val repositoryMoviesGenres: MoviesGenresRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) :
    UseCase<List<Movie>, GetNowPlayingMovies.Params> {
    override fun execute(params: Params): Single<List<Movie>> =
        Single.zip(
            repositoryMoviesGenres.fetch(),
            nowPlayingMoviesRepository.fetch(page = params.page),
            favoriteMoviesRepository.getFavoriteMoviesIds(),
            Function3() { moviesGenres, movies, ids ->
                movies.map { it.apply {
                    fillMovieGenres(moviesGenres, it)
                    like = ids.contains(this.id)
                } }
            })


    data class Params(val page: Int): Param()
}