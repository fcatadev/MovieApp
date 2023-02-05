package com.fcadev.movieapp.model.nowplayingmovies

data class NowPlayingMovies(
    val dates: Dates?,
    val page: Int?,
    val results: MutableList<NowPlayingResult>?,
    val total_pages: Int?,
    val total_results: Int?
)