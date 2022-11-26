package com.alcorp.efeeder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alcorp.efeeder.data.repository.AppRepository

class EfeedViewModel(private val repository: AppRepository) : ViewModel() {
    fun getDataKapasitas(): LiveData<String> = repository.getDataKapasitas()

    fun getHour(): LiveData<Int> = repository.getHour()

    fun getMinute(): LiveData<Int> = repository.getMinute()

    // Jam
    fun getDataJam1(): LiveData<String> = repository.TimeRepository().getDataJam1()

    fun setDataJam1(nilai: Int): LiveData<String> = repository.TimeRepository().setDataJam1(nilai)

    fun getDataJam2(): LiveData<String> = repository.TimeRepository().getDataJam2()

    fun setDataJam2(nilai: Int): LiveData<String> = repository.TimeRepository().setDataJam2(nilai)

    fun getDataJam3(): LiveData<String> = repository.TimeRepository().getDataJam3()

    fun setDataJam3(nilai: Int): LiveData<String> = repository.TimeRepository().setDataJam3(nilai)
    // End of Jam

    // Menit
    fun getDataMenit1(): LiveData<String> = repository.TimeRepository().getDataMenit1()

    fun setDataMenit1(nilai: Int): LiveData<String> = repository.TimeRepository().setDataMenit1(nilai)

    fun getDataMenit2(): LiveData<String> = repository.TimeRepository().getDataMenit2()

    fun setDataMenit2(nilai: Int): LiveData<String> = repository.TimeRepository().setDataMenit2(nilai)

    fun getDataMenit3(): LiveData<String> = repository.TimeRepository().getDataMenit3()

    fun setDataMenit3(nilai: Int): LiveData<String> = repository.TimeRepository().setDataMenit3(nilai)
    // End of Menit

    // Berat
    fun getDataBerat1(): LiveData<String> = repository.WeightRepository().getDataBerat1()

    fun setDataBerat1(nilai: Int): LiveData<String> = repository.WeightRepository().setDataBerat1(nilai)

    fun getDataBerat2(): LiveData<String> = repository.WeightRepository().getDataBerat2()

    fun setDataBerat2(nilai: Int): LiveData<String> = repository.WeightRepository().setDataBerat2(nilai)

    fun getDataBerat3(): LiveData<String> = repository.WeightRepository().getDataBerat3()

    fun setDataBerat3(nilai: Int): LiveData<String> = repository.WeightRepository().setDataBerat3(nilai)
    // End of Berat
}