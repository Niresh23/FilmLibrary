package com.geekbrains.team.filmlibrary


import com.geekbrains.team.filmlibrary.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins


class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { _: Throwable? -> }
    }
}