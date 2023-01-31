package com.fcadev.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcadev.movieapp.model.Movies

class PopularMoviesViewModel : ViewModel() {
    val movies = MutableLiveData<List<Movies>>()
    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()


}