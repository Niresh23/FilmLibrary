package com.geekbrains.team.filmlibrary.fragments.favorites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.geekbrains.team.domain.base.None
import com.geekbrains.team.domain.movies.favoriteMovies.interactor.AddFavoriteMovieIdUseCase
import com.geekbrains.team.domain.movies.favoriteMovies.interactor.GetFavoriteMoviesUseCase
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.waiting.interactor.GetWaitingMoviesUseCase
import com.geekbrains.team.domain.tv.favorite.interactor.GetFavoriteSeriesUseCase
import com.geekbrains.team.domain.tv.model.TVShow
import com.geekbrains.team.domain.tv.waiting.interactor.GetWaitingSeriesUseCase
import com.geekbrains.team.filmlibrary.base.BaseViewModel
import com.geekbrains.team.filmlibrary.model.MovieView
import com.geekbrains.team.filmlibrary.model.TVShowView
import com.geekbrains.team.filmlibrary.model.toMovieView
import com.geekbrains.team.filmlibrary.model.toTVShowView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase,
    private val getWaitingMoviesUseCase: GetWaitingMoviesUseCase,
    private val getWaitingSeriesUseCase: GetWaitingSeriesUseCase
) : BaseViewModel() {
    val favoriteMoviesLiveData = MutableLiveData<List<MovieView>>()
    val favoriteSeriesLiveData = MutableLiveData<List<TVShowView>>()
    val waitingMoviesLiveData = MutableLiveData<List<MovieView>>()
    val waitingSeriesLiveData = MutableLiveData<List<TVShowView>>()

    fun loadFavoriteMovies() {
        val disposable = getFavoriteMoviesUseCase.execute(None())
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadFavoriteMovies, ::handleFailure)

        addDisposable(disposable)
    }

    private fun handleOnSuccessLoadFavoriteMovies(data: List<Movie>) {
        favoriteMoviesLiveData.value = data.map { it.toMovieView() }
    }

    fun loadFavoriteSeries() {
        val disposable = getFavoriteSeriesUseCase.execute(None())
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadFavoriteSeries, ::handleFailure)
        addDisposable(disposable)
    }

    fun loadWaitingMovies() {
        val disposable = getWaitingMoviesUseCase.execute(None())
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadWaitingMovies, ::handleFailure)
        addDisposable(disposable)
    }

    fun loadWaitingSeries() {
        val disposable = getWaitingSeriesUseCase.execute(None())
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadWaitingSeries, ::handleFailure)
        addDisposable(disposable)
    }


    private fun handleOnSuccessLoadFavoriteSeries(data: List<TVShow>) {
        favoriteSeriesLiveData.value = data.map { it.toTVShowView() }
        Log.e("Favorite", data[0].name)
    }

    private fun handleOnSuccessLoadWaitingMovies(data: List<Movie>) {
        waitingMoviesLiveData.value = data.map { it.toMovieView() }
    }

    private fun handleOnSuccessLoadWaitingSeries(data: List<TVShow>) {
        waitingSeriesLiveData.value = data.map { it.toTVShowView() }
        Log.d("FavoriteViewModel.TV", data[0].name)
    }
}