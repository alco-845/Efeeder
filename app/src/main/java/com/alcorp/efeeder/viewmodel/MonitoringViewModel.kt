package com.alcorp.efeeder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alcorp.efeeder.data.repository.AppRepository

class MonitoringViewModel(private val repository: AppRepository) : ViewModel() {
    fun getHour(): LiveData<Int> = repository.getHour()

    fun getMinute(): LiveData<Int> = repository.getMinute()

    fun getDataSuhu(): LiveData<String> = repository.getDataSuhu()

    fun getDataOxygen(): LiveData<String> = repository.getDataOxygen()

    fun getDataPh(): LiveData<String> = repository.getDataPh()

    fun getDataSwitch(): LiveData<Int> = repository.SwitchRepository().getDataSwitch()

    fun setDataSwitch(nilai: Int) = repository.SwitchRepository().setDataSwitch(nilai)
}