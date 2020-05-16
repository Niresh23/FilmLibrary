package com.geekbrains.team.filmlibrary.fragments.mainScreen

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.nowPlayingMovies.interactor.GetFirstNowPlayingMovie
import com.geekbrains.team.domain.movies.nowPlayingMovies.interactor.GetNowPlayingMovies
import com.geekbrains.team.domain.movies.topRatedMovies.interactor.GetRandomTopRatedMovie
import com.geekbrains.team.domain.movies.upcomingMovies.interactor.GetUpcomingMovies
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.nowPlayingTV.interactor.GetFirstNowPlayingTV
import com.geekbrains.team.filmlibrary.addTo
import com.geekbrains.team.filmlibrary.base.BaseViewModel
import com.geekbrains.team.filmlibrary.model.MovieView
import com.geekbrains.team.filmlibrary.model.TVShowView
import com.geekbrains.team.filmlibrary.model.toMovieView
import com.geekbrains.team.filmlibrary.model.toTVShowView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val useCaseUpcomingMovies: GetUpcomingMovies,
    private val useCaseNowPlayingMovies: GetNowPlayingMovies,
    private val useCaseRandomTopRatedMovie: GetRandomTopRatedMovie,
    private val useCaseFirstNowPlayingMovie: GetFirstNowPlayingMovie,
    private val useCaseFirstNowPlayingTV: GetFirstNowPlayingTV
) :
    BaseViewModel() {
    val nowPlayingMoviesData: MutableLiveData<List<MovieView>> = MutableLiveData()
    val upcomingMoviesData: MutableLiveData<List<MovieView>> = MutableLiveData()
    val randomTopRatedMovieData: MutableLiveData<MovieView> = MutableLiveData()
    val movieOfTheWeekData: MutableLiveData<MovieView> = MutableLiveData()
    val tvShowPremierData: MutableLiveData<TVShowView> = MutableLiveData()

    fun loadNowPlayingMovies(page: Int = 1) {
        val disposable =
            useCaseNowPlayingMovies.execute(params = GetNowPlayingMovies.Params(page = page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleOnSuccessLoadNowPlayingMovies, ::handleFailure)

        addDisposable(disposable)
    }

    private fun handleOnSuccessLoadNowPlayingMovies(list: List<Movie>) {
        nowPlayingMoviesData.value = list.map { it.toMovieView() }
    }

    fun loadUpcomingMovies(page: Int = 1) {
        val disposable =
            useCaseUpcomingMovies.execute(params = GetUpcomingMovies.Params(page = page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleOnSuccessLoadUpcomingMovies, ::handleFailure)

        addDisposable(disposable)
    }

    private fun handleOnSuccessLoadUpcomingMovies(list: List<Movie>) {
        upcomingMoviesData.value = list.map { it.toMovieView() }
    }

    fun loadRandomTopRatedMovie() {
        useCaseRandomTopRatedMovie.execute(None())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadRandomTopRatedMovie, ::handleFailure)
            .addTo(compositeDisposable)
    }

    private fun handleOnSuccessLoadRandomTopRatedMovie(randomTopRatedMovie: Movie) {
        randomTopRatedMovieData.value = randomTopRatedMovie.toMovieView()
    }

    fun loadMovieOfTheWeek() {
        val disposable = useCaseFirstNowPlayingMovie.execute(params = None())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadMovieOfTheWeek, ::handleFailure)

        addDisposable(disposable)
    }

    private fun handleOnSuccessLoadMovieOfTheWeek(movie: Movie) {
        movieOfTheWeekData.value = movie.toMovieView()
    }

    fun loadTvShowPremier() {
        val disposable = useCaseFirstNowPlayingTV.execute(None())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadTvShowPremier, ::handleFailure)

        addDisposable(disposable)
    }

    private fun handleOnSuccessLoadTvShowPremier(tv: TVShow) {
        tvShowPremierData.value = tv.toTVShowView()
    }
}