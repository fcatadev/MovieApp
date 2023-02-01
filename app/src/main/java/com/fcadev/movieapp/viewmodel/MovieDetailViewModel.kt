package com.fcadev.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcadev.movieapp.model.trending.TrendingMovies

class MovieDetailViewModel: ViewModel() {

    val movieLiveData = MutableLiveData<TrendingMovies>()

    /*fun getDataFromApi(){
        val movie = TrendingMovies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )
        movieLiveData.value = movie
    }*/

}