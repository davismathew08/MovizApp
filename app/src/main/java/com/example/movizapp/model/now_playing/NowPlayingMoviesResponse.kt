package com.example.movizapp.model.now_playing

data class NowPlayingMoviesResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)