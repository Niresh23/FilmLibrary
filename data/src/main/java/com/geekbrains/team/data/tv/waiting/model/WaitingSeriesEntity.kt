package com.geekbrains.team.data.tv.waiting.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaitingSeriesEntity (
    @PrimaryKey
    val id: Int
)