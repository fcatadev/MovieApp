package com.fcadev.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcadev.movieapp.model.nowplayingmovies.NowPlayingMovies
import com.fcadev.movieapp.model.nowplayingmovies.NowPlayingResult
import com.fcadev.movieapp.service.getNowPlayingData.NowPlayingMovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val nowPlayingMovieApiService = NowPlayingMovieAPIService()
    private val disposable = CompositeDisposable()

    val nowPlayingMovies = MutableLiveData<MutableList<NowPlayingResult>?>()
    val nowPlayingMovieError = MutableLiveData<Boolean>()
    val nowPlayingMovieLoading = MutableLiveData<Boolean>()

    fun downloadData(){
        getNowPlayingDataFormAPI()
    }

    private fun getNowPlayingDataFormAPI() {
        nowPlayingMovieLoading.value = true

        disposable.add(
            nowPlayingMovieApiService.getNowPlayingMoviesData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NowPlayingMovies>(){
                    override fun onSuccess(t: NowPlayingMovies) {
                        nowPlayingMovies.value = t.results
                        nowPlayingMovieError.value = false
                        nowPlayingMovieLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        nowPlayingMovieError.value = true
                        nowPlayingMovieLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

}