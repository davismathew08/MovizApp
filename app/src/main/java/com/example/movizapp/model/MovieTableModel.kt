package com.example.movizapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class MovieTableModel (

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "pathlink")
    var pathlink: String

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}