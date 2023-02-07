package com.fcadev.movieapp.service.getTopRatedMoviesData

import com.fcadev.movieapp.model.topratedmovies.TopRatedMovies
import io.reactivex.Single
import retrofit2.http.GET

interface TopRatedMoviesAPI {

    //https://api.themoviedb.org/3/movie/top_rated?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1

    @GET("3/movie/top_rated?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1")
    fun getTopRatedMovies(): Single<TopRatedMovies>

}