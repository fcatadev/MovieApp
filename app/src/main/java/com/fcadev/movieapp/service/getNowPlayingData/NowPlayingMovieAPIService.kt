package com.fcadev.movieapp.service.getNowPlayingData

import com.fcadev.movieapp.model.nowplayingmovies.NowPlayingMovies
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NowPlayingMovieAPIService {
    // https://api.themoviedb.org/3/movie/now_playing?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1

    private val BASE_URL = "https://api.themoviedb.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NowPlayingMovieAPI::class.java)

    fun getNowPlayingMoviesData(): Single<NowPlayingMovies>{
        return api.getNowPlayingMovies()
    }

}