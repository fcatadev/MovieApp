package com.fcadev.movieapp.service.getNowPlayingData

import com.fcadev.movieapp.model.nowplayingmovies.NowPlayingMovies
import io.reactivex.Single
import retrofit2.http.GET

interface NowPlayingMovieAPI {
    // https://api.themoviedb.org/3/movie/now_playing?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1

    @GET("3/movie/now_playing?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1")
    fun getNowPlayingMovies(): Single<NowPlayingMovies>

}