package com.fcadev.movieapp.service.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMovieDao {

    @Insert
    fun insert(favoriteMovie: FavoriteMovie)

    @Delete
    fun delete(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): List<FavoriteMovie>

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    fun getMovieById(id: Int?): FavoriteMovie?
}