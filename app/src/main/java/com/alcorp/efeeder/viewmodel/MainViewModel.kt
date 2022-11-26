package com.alcorp.efeeder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alcorp.efeeder.data.repository.AppRepository

class MainViewModel(private val repository: AppRepository) : ViewModel() {
    fun getHour(): LiveData<Int> = repository.getHour()

    fun getMinute(): LiveData<Int> = repository.getMinute()
}