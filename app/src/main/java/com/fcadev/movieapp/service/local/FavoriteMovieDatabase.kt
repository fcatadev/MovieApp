package com.fcadev.movieapp.service.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMovieDatabase : RoomDatabase() {

    companion object{
        fun getDatabase(context: Context): FavoriteMovieDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                FavoriteMovieDatabase::class.java,
                "favorite_movies_db"
            ).build()
        }
    }

    abstract fun favoriteMovieDao(): FavoriteMovieDao
}