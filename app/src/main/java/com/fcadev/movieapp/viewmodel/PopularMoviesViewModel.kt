package com.fcadev.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcadev.movieapp.model.Movies

class PopularMoviesViewModel : ViewModel() {
    val movies = MutableLiveData<List<Movies>>()
    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val movie = Movies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )

        val movie1 = Movies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )

        val movie2 = Movies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )

        val movie3 = Movies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )

        val movie4 = Movies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )

        val movie5 = Movies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )

        val movie6 = Movies(
            "Avatar: The Way of Water",
            "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
            "2022-01-05",
            8.0f,
            1568,
            "www.ss.com"
        )

        val movieList = arrayListOf<Movies>(movie, movie1, movie2, movie3, movie4, movie5, movie6)
        movies.value = movieList
        movieError.value = false
        movieLoading.value = false
    }
}