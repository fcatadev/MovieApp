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
            8.0f,
            "www.ss.com"
        )

        val movie1 = Movies(
            "The Last of Us",
            "Twenty years after modern civilization has been destroyed, Joel, a hardened survivor, is hired to smuggle Ellie, a 14-year-old girl, out of an oppressive quarantine zone. What starts as a small job soon becomes a brutal, heartbreaking journey, as they both must traverse the United States and depend on each other for survival.",
            8.5f,
            "www.ss.com"
        )

        val movie2 = Movies(
            "M3GAN",
            "A brilliant toy company roboticist uses artificial intelligence to develop M3GAN, a life-like doll programmed to emotionally bond with her newly orphaned niece. But when the doll's programming works too well, she becomes overprotective of her new friend with terrifying results.",
            6.7f,
            "www.ss.com"
        )

        val movie3 = Movies(
            "Shotgun Wedding",
            "Darcy and Tom gather their families for the ultimate destination wedding but when the entire party is taken hostage, “’Til Death Do Us Part” takes on a whole new meaning in this hilarious, adrenaline-fueled adventure as Darcy and Tom must save their loved ones—if they don’t kill each other first.",
            7.4f,
            "www.ss.com"
        )

        val movie4 = Movies(
            "Shotgun Wedding",
            "Darcy and Tom gather their families for the ultimate destination wedding but when the entire party is taken hostage, “’Til Death Do Us Part” takes on a whole new meaning in this hilarious, adrenaline-fueled adventure as Darcy and Tom must save their loved ones—if they don’t kill each other first.",
            7.4f,
            "www.ss.com"
        )

        val movie5 = Movies(
            "Shotgun Wedding",
            "Darcy and Tom gather their families for the ultimate destination wedding but when the entire party is taken hostage, “’Til Death Do Us Part” takes on a whole new meaning in this hilarious, adrenaline-fueled adventure as Darcy and Tom must save their loved ones—if they don’t kill each other first.",
            7.4f,
            "www.ss.com"
        )

        val movie6 = Movies(
            "Shotgun Wedding",
            "Darcy and Tom gather their families for the ultimate destination wedding but when the entire party is taken hostage, “’Til Death Do Us Part” takes on a whole new meaning in this hilarious, adrenaline-fueled adventure as Darcy and Tom must save their loved ones—if they don’t kill each other first.",
            7.4f,
            "www.ss.com"
        )

        val movieList = arrayListOf<Movies>(movie, movie1, movie2, movie3, movie4, movie5, movie6)
        movies.value = movieList
        movieError.value = false
        movieLoading.value = false
    }
}