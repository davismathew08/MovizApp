package com.example.movizapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movizapp.data.room.MovieDatabase
import com.example.movizapp.model.MovieTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieRepository {

    companion object {

        var movieDatabase: MovieDatabase? = null

        var movieTableModel: LiveData<List<MovieTableModel>>? = null

        fun initializeDB(context: Context) : MovieDatabase {
            return MovieDatabase.getDataseClient(context)
        }

        fun insertData(context: Context, name: String, pathlink: String) {

            movieDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val movieDetails = MovieTableModel(name, pathlink)
                movieDatabase!!.movieDao().InsertData(movieDetails)
            }

        }

        fun getMovieDetails(context: Context) : LiveData<List<MovieTableModel>>? {

            movieDatabase = initializeDB(context)

            movieTableModel = movieDatabase!!.movieDao().getMovieDetails()

            return movieTableModel
        }

        fun deleteData(context: Context){
            movieDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                movieDatabase!!.movieDao().delete()
            }
        }

    }
}