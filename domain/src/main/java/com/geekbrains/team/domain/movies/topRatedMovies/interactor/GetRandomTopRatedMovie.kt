package com.geekbrains.team.domain.movies.topRatedMovies.interactor

import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.Images
import com.geekbrains.team.domain.base.model.Video
import com.geekbrains.team.domain.movies.commonRepository.MoviesImagesRepository
import com.geekbrains.team.domain.movies.commonRepository.VideosRepository
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.topRatedMovies.repository.TopRatedMoviesRepository
import com.geekbrains.team.domain.randomPage
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetRandomTopRatedMovie @Inject constructor(
    private val topRatedMoviesRepository: TopRatedMoviesRepository,
    private val moviesImagesRepository: MoviesImagesRepository,
    private val moviesVideoRepository: VideosRepository

) :
    UseCase<Movie, None> {
    override fun execute(params: None): Single<Movie> =
        topRatedMoviesRepository.fetch(randomPage())
            .flatMap { topRatedMovies ->
                Single.just(topRatedMovies.random())
                    .map { movie ->
                        Single.zip<Images, List<Video>, Unit>(
                            moviesImagesRepository.fetch(movie.id),
                            moviesVideoRepository.fetch(movie.id),
                            BiFunction { images, videos ->
                                movie.imagesNew = images
                                movie.videosNew = videos
                            })
                        movie
                    }
            }
}