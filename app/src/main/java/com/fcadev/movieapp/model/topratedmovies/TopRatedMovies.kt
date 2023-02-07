package com.fcadev.movieapp.model.topratedmovies

data class TopRatedMovies(
    val page: Int?,
    val results: List<TopRatedMoviesResult>?,
    val total_pages: Int?,
    val total_results: Int?
)