package com.fcadev.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcadev.movieapp.model.trending.Result
import com.fcadev.movieapp.model.trending.TrendingMovies
import com.fcadev.movieapp.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PopularMoviesViewModel : ViewModel() {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()

    val movies = MutableLiveData<MutableList<Result>?>()
    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        getDataFromAPI()
    }

    private fun getDataFromAPI(){
        movieLoading.value = true

        disposable.add(
            movieApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TrendingMovies>(){
                    override fun onSuccess(t: TrendingMovies) {
                        movies.value = t.results
                        movieError.value = false
                        movieLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        movieLoading.value = false
                        movieError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }
}