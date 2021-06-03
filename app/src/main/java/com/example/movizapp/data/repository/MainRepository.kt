package com.example.movizapp.data.repository

import com.example.movizapp.data.api.ApiService
import com.example.movizapp.model.movie_details.MovieDetailsResponse
import com.example.movizapp.model.now_playing.NowPlayingMoviesResponse
import com.example.movizapp.model.now_playing.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class MainRepository(private val apiService: ApiService) {
    suspend fun nowPlayingMovieListDetails(api_key: String,page: Int): NowPlayingMoviesResponse =
        apiService.nowPlayingMovieListDetails(api_key,page)

    suspend fun getMovieDetails(movie_id: String, api_key: String): Response<MovieDetailsResponse> =
        apiService.getMovieDetails(movie_id,api_key)

}
