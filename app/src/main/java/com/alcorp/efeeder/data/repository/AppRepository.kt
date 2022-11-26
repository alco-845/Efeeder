package com.alcorp.efeeder.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alcorp.efeeder.data.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AppRepository(private val apiService: ApiService) {
    fun getHour(): MutableLiveData<Int> {
        val data = MutableLiveData<Int>()

        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]

        data.value = hour

        return data
    }

    fun getMinute(): MutableLiveData<Int> {
        val data = MutableLiveData<Int>()

        val calendar = Calendar.getInstance()
        val minute = calendar[Calendar.MINUTE]

        data.value = minute

        return data
    }

    fun getDataSuhu(): MutableLiveData<String> {
        val data = MutableLiveData<String>()

        apiService.getDataSuhu().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })

        return data
    }

    fun getDataOxygen(): MutableLiveData<String> {
        val data = MutableLiveData<String>()

        apiService.getDataOxygen().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })

        return data
    }

    fun getDataPh(): LiveData<String> {
        val data = MutableLiveData<String>()

        apiService.getDataPh().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })

        return data
    }

    fun getDataKapasitas(): LiveData<String> {
        val data = MutableLiveData<String>()

        apiService.getDataKapasitas().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })

        return data
    }

    inner class TimeRepository() {
        //    Start of Jam
        fun getDataJam1(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataJam1().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataJam1(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataJam1(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun getDataJam2(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataJam2().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataJam2(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataJam2(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun getDataJam3(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataJam3().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataJam3(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataJam3(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }
        //    End of Jam

        //    Start of Menit
        fun getDataMenit1(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataMenit1().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataMenit1(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataMenit1(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun getDataMenit2(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataMenit2().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataMenit2(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataMenit2(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun getDataMenit3(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataMenit3().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataMenit3(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataMenit3(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }
        //    End of Menit
    }

    inner class WeightRepository() {
        fun getDataBerat1(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataBerat1().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataBerat1(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataBerat1(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun getDataBerat2(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataBerat2().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataBerat2(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataBerat2(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun getDataBerat3(): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.getDataBerat3().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataBerat3(nilai: Int): LiveData<String> {
            val data = MutableLiveData<String>()

            apiService.setDataBerat3(nilai).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

            return data
        }
    }

    inner class SwitchRepository() {
        fun getDataSwitch(): MutableLiveData<Int> {
            val data = MutableLiveData<Int>()

            apiService.getDataSwitch().enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                }
            })

            return data
        }

        fun setDataSwitch(nilai: Int) {
            apiService.setDataSwitch(nilai).enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                }
            })
        }
    }

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(apiService)
            }.also { instance = it }
    }
}