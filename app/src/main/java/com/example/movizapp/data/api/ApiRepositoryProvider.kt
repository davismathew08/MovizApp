package com.example.movizapp.data.api

import com.example.movizapp.data.repository.MainRepository

object ApiRepositoryProvider {
    fun providerApiRepository(): MainRepository {
        return MainRepository(ApiService.create())
    }
}