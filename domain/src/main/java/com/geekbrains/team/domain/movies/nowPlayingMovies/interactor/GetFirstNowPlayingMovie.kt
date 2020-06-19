package com.geekbrains.team.domain.movies.nowPlayingMovies.interactor

import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.movies.commonRepository.VideosRepository
import com.geekbrains.team.domain.movies.favoriteMovies.repository.FavoriteMoviesRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.nowPlayingMovies.repository.NowPlayingMoviesRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class GetFirstNowPlayingMovie @Inject constructor(
    private val nowPlayingMoviesRepository: NowPlayingMoviesRepository,
    @param:Named("MovieVideos") private val moviesVideoRepository: VideosRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) :
    UseCase<Movie, None> {
    override fun execute(params: None): Single<Movie> =
        nowPlayingMoviesRepository.fetch()
            .flatMap { movies -> Single.just(movies.first()) }
            .flatMap { movie ->
                moviesVideoRepository.fetch(movie.id).map {
                    movie.videos = it
                    movie
                }
                favoriteMoviesRepository.getFavoriteMoviesIds().map {
                    movie.like = it.contains(movie.id)
                    movie
                }
            }
}