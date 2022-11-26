package com.alcorp.efeeder.di

import com.alcorp.efeeder.data.network.ApiConfig
import com.alcorp.efeeder.data.repository.AppRepository

object Injection {
    fun provideRepository(): AppRepository {
        val apiService = ApiConfig.getApiService()
        return AppRepository.getInstance(apiService)
    }
}