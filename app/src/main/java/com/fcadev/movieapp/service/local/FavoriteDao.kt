package com.fcadev.movieapp.service.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fcadev.movieapp.model.trending.Result

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insertAll(vararg favorites: Result) : List<Long>

    @Query("SELECT * FROM result")
    suspend fun gelAllShows() : List<Result>

    @Query("SELECT * FROM result WHERE uuid = :showId")
    suspend fun getFavoritesShow(showId: Int) : Result

    @Query("DELETE FROM result")
    suspend fun deleteAllShow()

}