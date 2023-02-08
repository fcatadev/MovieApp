package com.fcadev.movieapp.service.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMovieDao {

    @Insert
    fun insert(favoriteMovie: FavoriteMovie)

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): List<FavoriteMovie>
}