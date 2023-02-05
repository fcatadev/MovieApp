package com.fcadev.movieapp.model.topratedtvshows

data class TopRatedTvShows(
    val page: Int?,
    val results: List<TopRatedTvShowsResult>?,
    val total_pages: Int?,
    val total_results: Int?
)