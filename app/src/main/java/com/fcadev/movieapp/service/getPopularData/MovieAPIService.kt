package com.fcadev.movieapp.service.getPopularData

import com.fcadev.movieapp.model.trending.TrendingMovies
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {
    //https://api.themoviedb.org/3/trending/all/week?api_key=df0d08ee2aefee4a812108d5ed8cce11

    private val BASE_URL = "https://api.themoviedb.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getData(): Single<TrendingMovies> {
        return api.getMovies()
    }

}