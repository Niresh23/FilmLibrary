package com.geekbrains.team.filmlibrary.di

import androidx.lifecycle.ViewModel
import com.geekbrains.team.filmlibrary.fragments.movieDetails.FullFilmInfoFragment
import com.geekbrains.team.filmlibrary.fragments.movieDetails.FullFilmInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsFragmentModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )

    internal abstract fun movieDetailsFragment(): FullFilmInfoFragment

    @Binds
    @IntoMap
    @ViewModelKey(FullFilmInfoViewModel::class)
    abstract fun bindViewModel(viewModel: FullFilmInfoViewModel): ViewModel
}