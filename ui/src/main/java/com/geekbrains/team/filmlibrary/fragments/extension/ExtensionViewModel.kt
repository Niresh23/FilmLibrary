package com.geekbrains.team.filmlibrary.fragments.extension

import android.util.Log
import com.geekbrains.team.filmlibrary.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import com.geekbrains.team.domain.actors.credits.interactor.GetActorMovieCreditsUseCase
import com.geekbrains.team.domain.actors.credits.interactor.GetActorTVCreditsUseCase
import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import com.geekbrains.team.domain.movies.model.Movie
import com.geekbrains.team.domain.movies.movieDetails.interactor.GetMovieActorsUseCase
import com.geekbrains.team.domain.movies.movieDetails.interactor.GetMovieCrewUseCase
import com.geekbrains.team.domain.movies.nowPlayingMovies.interactor.GetNowPlayingMovies
import com.geekbrains.team.domain.movies.similarMovie.interactor.GetSimilarMoviesUseCase
import com.geekbrains.team.domain.movies.upcomingMovies.interactor.GetUpcomingMovies
import com.geekbrains.team.filmlibrary.adapters.UseCasesEnum
import com.geekbrains.team.filmlibrary.addTo
import com.geekbrains.team.filmlibrary.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import java.lang.IllegalArgumentException
import javax.inject.Inject


class ExtensionViewModel @Inject constructor(
    private val similarMoviesUseCase: GetSimilarMoviesUseCase,
    private val movieActorsUseCase: GetMovieActorsUseCase,
    private val movieCrewUseCase: GetMovieCrewUseCase,
    private val nowPlayingMoviesUseCase: GetNowPlayingMovies,
    private val upcomingMoviesUseCase: GetUpcomingMovies,
    private val getActorMovieCreditsUseCase: GetActorMovieCreditsUseCase,
    private val getActorTVCreditsUseCase: GetActorTVCreditsUseCase
): BaseViewModel() {

    companion object {
        private const val FIRST_PAGE = 1
    }
    private var currentPage = 1
    val movieLiveData = MutableLiveData<List<MovieView>>()
    val personLiveData = MutableLiveData<List<PersonView>>()
    val tvShowLiveData = MutableLiveData<List<TVShowView>>()

    fun getData(argument: String, id: Int = 1, page: Int = 1) {
        when(argument) {
            UseCasesEnum.NOW_PLAYING_MOVIES.name -> {
                Log.d("loadMore($argument)", id.toString())
                nowPlayingMoviesUseCase.execute(GetNowPlayingMovies.Params(currentPage))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::movieHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.UPCOMING_MOVIES.name -> {
                Log.d("loadMore($argument)", id.toString())
                upcomingMoviesUseCase.execute(GetUpcomingMovies.Params(currentPage))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::movieHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.SIMILAR_MOVIE.name -> {
                Log.d("loadMore($argument)", id.toString())
                similarMoviesUseCase.execute(GetSimilarMoviesUseCase.Params(id, page))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::movieHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.MOVIE_ACTORS.name -> {
                Log.d("loadMore($argument)", id.toString())
                movieActorsUseCase.execute(GetMovieActorsUseCase.Params(id))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::actorHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.MOVIE_CREW.name -> {
                Log.d("loadMore($argument)", id.toString())
                movieCrewUseCase.execute(GetMovieCrewUseCase.Params(id))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::crewHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.ACTING_IN_MOVIES.name -> {
                Log.d("loadMore($argument)", id.toString())
                getActorMovieCreditsUseCase.execute(GetActorMovieCreditsUseCase.Params(id))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::actorHandler, ::handleFailure).addTo(compositeDisposable)
            }

            UseCasesEnum.ACTING_IN_TV_SHOWS.name -> {
                Log.d("loadMore($argument)", id.toString())
                getActorTVCreditsUseCase.execute(GetActorTVCreditsUseCase.Params(id))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::actorHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.PRODUCT_MOVIES.name -> {
                Log.d("loadMore($argument)", id.toString())
                getActorMovieCreditsUseCase.execute(GetActorMovieCreditsUseCase.Params(id))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::crewHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.PRODUCT_TV_SHOWS.name -> {
                Log.d("loadMore($argument)", id.toString())
                getActorTVCreditsUseCase.execute(GetActorTVCreditsUseCase.Params(id))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::crewHandler, ::handleFailure).addTo(compositeDisposable)
            }
            else -> throw IllegalArgumentException("Not such UseCase")
        }
    }

    fun loadMore(argument: String, id: Int = 1) {
        when(argument){
            UseCasesEnum.NOW_PLAYING_MOVIES.name -> {
                nowPlayingMoviesUseCase.execute(GetNowPlayingMovies.Params(++currentPage))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::moreMovieHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.UPCOMING_MOVIES.name -> {
                upcomingMoviesUseCase.execute(GetUpcomingMovies.Params(++currentPage))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::moreMovieHandler, ::handleFailure).addTo(compositeDisposable)
            }
            UseCasesEnum.SIMILAR_MOVIE.name -> {
                similarMoviesUseCase.execute(GetSimilarMoviesUseCase.Params(page = ++currentPage, id = id))
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::moreMovieHandler, ::handleFailure).addTo(compositeDisposable)
            }
        }
    }

    private fun movieHandler(list: List<Movie>) {
        movieLiveData.value = list.map { it.toMovieView() }
    }

    private fun moreMovieHandler(list: List<Movie>) {
        movieLiveData.value = movieLiveData.value?.toMutableList().apply {
            this?.addAll(list.map { it.toMovieView() })
        }
    }

    private fun actorHandler(list: List<Movie.Actor>?) {
        personLiveData.value = list?.map { it.toPersonView() }
    }

    private fun actorHandler(actorInfo: ActorCreditsInfo) {
        Log.d("extensionVM actor", actorInfo.id.toString())
        personLiveData.value = actorInfo.cast.map { it.toPersonView() }
    }

    private fun crewHandler(list: List<Movie.Member>?) {
        personLiveData.value = list?.map { it.toPersonView() }
    }

    private fun crewHandler(actorInfo: ActorCreditsInfo) {
        Log.d("extensionVM crew", actorInfo.id.toString())
        personLiveData.value = actorInfo.crew.map { it.toPersonView() }
    }


}