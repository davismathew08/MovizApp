package com.example.movizapp.ui.main.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movizapp.data.api.ApiRepositoryProvider
import com.example.movizapp.model.movie_details.MovieDetailsResponse
import com.example.movizapp.utils.Constants
import com.example.movizapp.utils.Resource
import kotlinx.coroutines.launch

class MovieDetailsViewModel: ViewModel() {
    private val movieDetailsResponseLiveData= MutableLiveData<Resource<MovieDetailsResponse>>()

    fun fetchMovieDetails(movie_id:String){
        val repository= ApiRepositoryProvider.providerApiRepository()
        viewModelScope.launch {
            movieDetailsResponseLiveData.postValue(Resource.loading(null))
            try {
                repository.getMovieDetails(movie_id,"4c7ffdfde6c9449d5aae4a479fed8089").let {
                    val response=it.body()
                    if(response!=null){
                        movieDetailsResponseLiveData.postValue(Resource.success(response))
                    }else
                    {
                        movieDetailsResponseLiveData.postValue(Resource.error("null",response))
                    }
                }

            }catch (ex:Exception){
                movieDetailsResponseLiveData.postValue(Resource.noInternet("",null))
            }
        }
    }

    fun getMovieDetailsResponse(): LiveData<Resource<MovieDetailsResponse>> {
        return movieDetailsResponseLiveData
    }
}