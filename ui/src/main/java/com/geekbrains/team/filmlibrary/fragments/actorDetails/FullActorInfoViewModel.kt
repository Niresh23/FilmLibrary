package com.geekbrains.team.filmlibrary.fragments.actorDetails

import android.util.Log
import com.geekbrains.team.domain.actors.credits.interactor.GetActorMovieCreditsUseCase
import com.geekbrains.team.domain.actors.credits.interactor.GetActorTVCreditsUseCase
import com.geekbrains.team.domain.actors.details.interactor.GetActorDetailsUseCase
import com.geekbrains.team.filmlibrary.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import com.geekbrains.team.domain.actors.model.ActorCreditsInfo
import com.geekbrains.team.domain.actors.model.ActorInformation
import com.geekbrains.team.filmlibrary.model.CreditsView
import com.geekbrains.team.filmlibrary.model.toCreditsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io

import javax.inject.Inject

class FullActorInfoViewModel @Inject constructor(
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val getActorMovieCreditsUseCase: GetActorMovieCreditsUseCase,
    private val getActorTVCreditsUseCase: GetActorTVCreditsUseCase
): BaseViewModel() {
    val detailsLiveData = MutableLiveData<ActorInformation>()
    val movieCreditsLiveData = MutableLiveData<CreditsView>()
    val tvCreditsLiveData= MutableLiveData<CreditsView>()

    fun loadDetails(id: Int) {
        val disposable = getActorDetailsUseCase.execute(params = GetActorDetailsUseCase.Params(id = id))
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadActorDetails, ::handleFailure)
        addDisposable(disposable)
    }
    fun loadMovieCredits(id: Int) {
        val disposable = getActorMovieCreditsUseCase.execute(params = GetActorMovieCreditsUseCase.Params(id = id))
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadMovieCredits, ::handleFailure)
        addDisposable(disposable)
    }

    fun loadTVCredits(id: Int) {
        val disposable = getActorTVCreditsUseCase.execute(params = GetActorTVCreditsUseCase.Params(id = id))
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleOnSuccessLoadTVCredits, ::handleFailure)
        addDisposable(disposable)
    }

    private fun handleOnSuccessLoadActorDetails(details: ActorInformation) {
        detailsLiveData.value = details
        Log.e("VIEW_MODEL", details.name)
    }

    private fun handleOnSuccessLoadMovieCredits(credits: ActorCreditsInfo) {
        movieCreditsLiveData.value = credits.toCreditsView()
        Log.e("VIEW_MODEL", credits.id.toString())
    }

    private fun handleOnSuccessLoadTVCredits(credits: ActorCreditsInfo) {
        tvCreditsLiveData.value = credits.toCreditsView()
        Log.e("VIEW_MODEL", credits.id.toString())
    }
}