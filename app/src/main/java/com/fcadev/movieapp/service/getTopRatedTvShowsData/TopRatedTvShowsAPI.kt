package com.fcadev.movieapp.service.getTopRatedTvShowsData

import com.fcadev.movieapp.model.topratedtvshows.TopRatedTvShows
import io.reactivex.Single
import retrofit2.http.GET

interface TopRatedTvShowsAPI {

    //https://api.themoviedb.org/3/tv/top_rated?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1

    @GET("3/tv/top_rated?api_key=df0d08ee2aefee4a812108d5ed8cce11&language=en-US&page=1")
    fun getTopRatedTvShows(): Single<TopRatedTvShows>

}