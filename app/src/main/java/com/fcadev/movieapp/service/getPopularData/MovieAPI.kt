package com.fcadev.movieapp.service.getPopularData

import com.fcadev.movieapp.model.trending.TrendingMovies
import io.reactivex.Single
import retrofit2.http.GET

interface MovieAPI {

    //GET POST
    //https://api.themoviedb.org/3/trending/all/week?api_key=df0d08ee2aefee4a812108d5ed8cce11

    @GET("3/trending/all/week?api_key=df0d08ee2aefee4a812108d5ed8cce11")
    fun getMovies(): Single<TrendingMovies>

}