package com.geekbrains.team.filmlibrary.di

import androidx.lifecycle.ViewModel
import com.geekbrains.team.filmlibrary.fragments.extension.ExtensionFragment
import com.geekbrains.team.filmlibrary.fragments.extension.ExtensionViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ExtensionFragmentModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun extensionFragment(): ExtensionFragment

    @Binds
    @IntoMap
    @ViewModelKey(ExtensionViewModel::class)
    abstract fun bindViewModel(viewModel: ExtensionViewModel): ViewModel
}