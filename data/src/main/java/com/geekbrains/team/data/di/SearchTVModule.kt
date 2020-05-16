package com.geekbrains.team.data.di

import com.geekbrains.team.data.tv.searchTV.repository.SearchTVRepositoryImpl
import com.geekbrains.team.domain.tv.searchTV.repository.SearchTVRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class SearchTVModule {
    @Binds
    @Singleton
    abstract fun provideRepositorySearchTVShow(repository: SearchTVRepositoryImpl): SearchTVRepository
}