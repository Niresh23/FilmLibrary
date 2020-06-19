package com.geekbrains.team.data.movies.waiting.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaitingMovieEntity (
    @PrimaryKey
    val id: Int
)