package com.fcadev.movieapp.service.getTopRatedTvShowsData

import com.fcadev.movieapp.model.topratedtvshows.TopRatedTvShows
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TopRatedTvShowsAPIService {

    //https://api.themoviedb.org/3/tv/top_rated?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1

    private val BASE_URL = "https://api.themoviedb.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TopRatedTvShowsAPI::class.java)

    fun getTopRatedTvShowsData(): Single<TopRatedTvShows> {
        return api.getTopRatedTvShows()
    }

}