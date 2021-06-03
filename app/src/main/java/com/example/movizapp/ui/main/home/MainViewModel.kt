package com.example.movizapp.ui.main.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movizapp.data.api.ApiRepositoryProvider
import com.example.movizapp.data.repository.MainRepository
import com.example.movizapp.data.repository.MovieRepository
import com.example.movizapp.data.repository.paging_details.MoviesPageSource
import com.example.movizapp.model.MovieTableModel
import com.example.movizapp.model.now_playing.NowPlayingMoviesResponse
import com.example.movizapp.model.now_playing.Result
import kotlinx.coroutines.flow.Flow

class MainViewModel():ViewModel() {
    val moviesList: Flow<PagingData<Result>> = getMovieList()

    private fun getMovieList(): Flow<PagingData<Result>> {
        val repository= ApiRepositoryProvider.providerApiRepository()
        return Pager(PagingConfig(20)) {
            MoviesPageSource(repository)
        }.flow
    }

    var liveDataMovie: LiveData<List<MovieTableModel>>? = null

    fun insertData(context: Context, name: String, pathlink: String) {
        MovieRepository.insertData(context, name, pathlink)
    }
    fun deleteData(context: Context) {
        MovieRepository.deleteData(context)
    }

    fun getMovieDetails(context: Context) :LiveData<List<MovieTableModel>>? {
        liveDataMovie = MovieRepository.getMovieDetails(context)
        return liveDataMovie
    }
}