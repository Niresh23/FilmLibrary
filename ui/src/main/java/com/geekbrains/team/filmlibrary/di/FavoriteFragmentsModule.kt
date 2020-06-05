package com.geekbrains.team.filmlibrary.di

import androidx.lifecycle.ViewModel
import com.geekbrains.team.filmlibrary.fragments.favorites.FavoriteFragment
import com.geekbrains.team.filmlibrary.fragments.favorites.FavoriteMovieFragment
import com.geekbrains.team.filmlibrary.fragments.favorites.FavoriteTVShowFragment
import com.geekbrains.team.filmlibrary.fragments.favorites.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteFragmentsModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun favoriteMovieFragment(): FavoriteMovieFragment

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun favoriteTVShowFragment(): FavoriteTVShowFragment

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindViewModel(viewModel: FavoriteViewModel): ViewModel


}