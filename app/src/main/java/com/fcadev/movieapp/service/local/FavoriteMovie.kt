package com.fcadev.movieapp.service.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_movies")
data class FavoriteMovie (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val title: String?,
    val poster_path: String?
)