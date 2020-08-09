package com.geekbrains.team.domain.base

import android.os.Parcelable
import com.geekbrains.team.domain.base.model.Param
import io.reactivex.Completable
import io.reactivex.Single
import java.io.Serializable

interface UseCase<out R, in Params: Param> {
    fun execute(params: Params): Single<out R>
}

interface UseCaseCompletable<in Params> {
    fun execute(params: Params): Completable
}

class None: Param()