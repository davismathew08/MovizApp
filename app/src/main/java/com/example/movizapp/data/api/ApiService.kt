package com.example.movizapp.data.api

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.movizapp.model.movie_details.MovieDetailsResponse
import com.example.movizapp.model.now_playing.NowPlayingMoviesResponse
import com.example.movizapp.model.now_playing.Result
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {

    //now_playing
    @GET("now_playing")
    suspend fun nowPlayingMovieListDetails(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): NowPlayingMoviesResponse

    //get movie details
    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ): Response<MovieDetailsResponse>


    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        fun create(): ApiService {
            val retrofit = retrofit2.Retrofit.Builder()
                .client(initializeClient())
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)

        }

        private fun initializeClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            System.setProperty("http.keepAlive", "false");
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(interceptor)
                .addInterceptor(SupportInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)


            return builder.build()
        }


    }
}