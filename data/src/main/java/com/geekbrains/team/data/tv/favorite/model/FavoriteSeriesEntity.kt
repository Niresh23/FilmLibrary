package com.geekbrains.team.data.tv.favorite.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteSeriesEntity (
    @PrimaryKey
    val id: Int
)