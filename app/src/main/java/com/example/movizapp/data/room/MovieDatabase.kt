package com.example.movizapp.data.room

import android.content.Context
import androidx.room.*
import com.example.movizapp.model.MovieTableModel


@Database(entities = arrayOf(MovieTableModel::class), version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDataseClient(context: Context) : MovieDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, MovieDatabase::class.java, "MOVIE_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}