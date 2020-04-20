package com.geekbrains.team.filmlibrary.fragments.mainScreen

import androidx.lifecycle.MutableLiveData
import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.nowPlayingMovies.interactor.GetFirstNowPlayingMovie
import com.geekbrains.team.domain.movies.nowPlayingMovies.interactor.GetNowPlayingMovies
import com.geekbrains.team.domain.movies.topRatedMovies.interactor.GetRandomTopRatedMovie
import com.geekbrains.team.domain.movies.upcomingMovies.interactor.GetUpcomingMovies
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.nowPlayingTV.interactor.GetFirstNowPlayingTV
import com.geekbrains.team.filmlibrary.base.BaseViewModel
import com.geekbrains.team.filmlibrary.fragments.mainScreen.model.NowPlayingMovieView
import com.geekbrains.team.filmlibrary.fragments.mainScreen.model.UpcomingMovieView
import com.geekbrains.team.filmlibrary.fragments.mainScreen.model.toNowPlayingMovieView
import com.geekbrains.team.filmlibrary.fragments.mainScreen.model.toUpcomingMovieView
import com.geekbrains.team.filmlibrary.model.MovieView
import com.geekbrains.team.filmlibrary.model.TVShowView
import com.geekbrains.team.filmlibrary.model.toMovieView
import com.geekbrains.team.filmlibrary.model.toTVShowView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val useCaseUpcomingMovies: GetUpcomingMovies,
    private val useCaseNowPlayingMovies: GetNowPlayingMovies,
    private val useCaseRandomTopRatedMovie: GetRandomTopRatedMovie,
    private val useCaseFirstNowPlayingMovie: GetFirstNowPlayingMovie,
    private val useCaseFirstNowPlayingTV: GetFirstNowPlayingTV
) :
    BaseViewModel() {
    val nowPlayingMoviesData: MutableLiveData<List<NowPlayingMovieView>> = MutableLiveData()
    val upcomingMoviesData: MutableLiveData<List<UpcomingMovieView>> = MutableLiveData()
    val randomTopRatedMovieData: MutableLiveData<MovieView> = MutableLiveData()
    val movieOfTheWeekData: MutableLiveData<NowPlayingMovieView> = MutableLiveData()
    val tvShowPremierData: MutableLiveData<TVShowView> = MutableLiveData()

    fun loadNowPlayingMovies(page: Int) =
        useCaseNowPlayingMovies.execute(params = GetNowPlayingMovies.Params(page = page))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadNowPlayingMovies, ::handleFailure)

    private fun handleOnSuccessLoadNowPlayingMovies(list: List<Movie>) {
        nowPlayingMoviesData.value = list.map { it.toNowPlayingMovieView() }
    }

    fun loadUpcomingMovies(page: Int) =
        useCaseUpcomingMovies.execute(params = GetUpcomingMovies.Params(page = page))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadUpcomingMovies, ::handleFailure)

    private fun handleOnSuccessLoadUpcomingMovies(list: List<Movie>) {
        upcomingMoviesData.value = list.map { it.toUpcomingMovieView() }
    }

    fun loadRandomTopRatedMovie() =
        useCaseRandomTopRatedMovie.execute(None())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadRandomTopRatedMovie, ::handleFailure)

    private fun handleOnSuccessLoadRandomTopRatedMovie(randomTopRatedMovie: Movie) {
        randomTopRatedMovieData.value = randomTopRatedMovie.toMovieView()
    }

    fun loadMovieOfTheWeek() =
        useCaseFirstNowPlayingMovie.execute(params = None())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadMovieOfTheWeek, ::handleFailure)

    private fun handleOnSuccessLoadMovieOfTheWeek(movie: Movie) {
        movieOfTheWeekData.value = movie.toNowPlayingMovieView()
    }

    fun loadTvShowPremier(page: Int) =
        useCaseFirstNowPlayingTV.execute(None())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadTvShowPremier, ::handleFailure)

    private fun handleOnSuccessLoadTvShowPremier(tv: TVShow) {
        tvShowPremierData.value = tv.toTVShowView()
    }
}