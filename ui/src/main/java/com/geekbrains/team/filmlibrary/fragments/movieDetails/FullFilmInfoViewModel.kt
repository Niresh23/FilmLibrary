package com.geekbrains.team.filmlibrary.fragments.movieDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.geekbrains.team.domain.movies.favoriteMovies.interactor.AddFavoriteMovieIdUseCase
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.movieDetails.interactor.GetMovieDetailsUseCase
import com.geekbrains.team.domain.movies.similarMovie.interactor.GetSimilarMoviesUseCase
import com.geekbrains.team.filmlibrary.base.BaseViewModel
import com.geekbrains.team.filmlibrary.model.PersonView
import com.geekbrains.team.filmlibrary.model.MovieView
import com.geekbrains.team.filmlibrary.model.toPersonView
import com.geekbrains.team.filmlibrary.model.toMovieView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class FullFilmInfoViewModel @Inject constructor(
    private val useCaseMovieInfo: GetMovieDetailsUseCase,
    private val useCaseSimilarMovies: GetSimilarMoviesUseCase,
    private val addFavoriteMovieIdUseCase: AddFavoriteMovieIdUseCase
) : BaseViewModel() {

    val movieDetailsLiveData: MutableLiveData<MovieView> = MutableLiveData()
    val actorsLiveData: MutableLiveData<List<PersonView>> = MutableLiveData()
    val crewLiveData: MutableLiveData<List<PersonView>> = MutableLiveData()
    val similarMoviesLiveData: MutableLiveData<List<MovieView>> = MutableLiveData()

    var currentMoviePoster = 0

    fun loadMovieInfo(id: Int) {
        val disposable = useCaseMovieInfo.execute(params = GetMovieDetailsUseCase.Params(id = id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadMovieDetails, ::handleFailure)

        addDisposable(disposable)
    }

    fun loadSimilarMovies(id: Int, page: Int? = null) {
        val disposable = useCaseSimilarMovies.execute(GetSimilarMoviesUseCase.Param(id, page))
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadSimilarMovies, ::handleFailure)
        addDisposable(disposable)
    }

    fun addInFavorite(id: Int) {
        addFavoriteMovieIdUseCase.execute(AddFavoriteMovieIdUseCase.Params(id)).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : DisposableCompletableObserver(){
            override fun onComplete() {
                Log.d("addInFavorite() ", "Success")
            }

            override fun onError(e: Throwable) {
                Log.d("addInFavorite() ", e.message ?: "Error")
            }
        })
    }

    private fun handleOnSuccessLoadMovieDetails(details: Movie) {
        movieDetailsLiveData.value = details.toMovieView()
        actorsLiveData.value = details.cast?.map { it.toPersonView() }
        crewLiveData.value = details.crew?.map { it.toPersonView() }
    }

    private fun handleOnSuccessLoadSimilarMovies(movies: List<Movie>) {
        similarMoviesLiveData.value = movies.map { it.toMovieView() }
    }


}