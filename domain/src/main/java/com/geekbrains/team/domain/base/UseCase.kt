package com.geekbrains.team.domain.base

import android.os.Parcelable
import io.reactivex.Completable
import io.reactivex.Single
import java.io.Serializable

interface UseCase<out R, in Params>: Serializable {
    fun execute(params: Params): Single<out R>
}

interface UseCaseCompletable<in Params> {
    fun execute(params: Params): Completable
}

class None