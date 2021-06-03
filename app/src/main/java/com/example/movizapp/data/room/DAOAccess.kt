package com.example.movizapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movizapp.model.MovieTableModel


@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(movieTableModel: MovieTableModel)

    @Query("SELECT * FROM Movie")
    fun getMovieDetails() : LiveData<List<MovieTableModel>>

    @Query("DELETE FROM Movie")
    fun delete()

}