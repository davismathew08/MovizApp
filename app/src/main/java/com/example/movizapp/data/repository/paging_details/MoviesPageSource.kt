package com.example.movizapp.data.repository.paging_details

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movizapp.data.api.ApiService
import com.example.movizapp.data.repository.MainRepository
import com.example.movizapp.model.now_playing.Result
import com.example.movizapp.ui.main.home.MainViewModel
import com.google.gson.Gson

import retrofit2.HttpException
import java.io.IOException

class MoviesPageSource(private val mainRepository: MainRepository) : PagingSource<Int,Result>() {

    private val DEFAULT_PAGE_INDEX= 1


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = mainRepository.nowPlayingMovieListDetails("4c7ffdfde6c9449d5aae4a479fed8089",page)

            LoadResult.Page(
                response.results,
                prevKey = if(page == DEFAULT_PAGE_INDEX) null else page-1,
                nextKey = if(response.results.isEmpty()) null else page+1
            )

        } catch (exception:IOException){
            LoadResult.Error(exception)
        } catch (exception:HttpException){
            LoadResult.Error(exception)
        }
    }
}
