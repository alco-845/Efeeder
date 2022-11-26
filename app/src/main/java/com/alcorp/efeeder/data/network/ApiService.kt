package com.alcorp.efeeder.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val token = "fThx9bU1f14pilQ5D7-YRokyD-Bhcf21"

interface ApiService {
    @GET("get?token=$token&v0")
    fun getDataSuhu() : Call<String>

    @GET("get?token=$token&v1")
    fun getDataOxygen() : Call<String>

    @GET("get?token=$token&v2")
    fun getDataPh() : Call<String>

    @GET("get?token=$token&v3")
    fun getDataKapasitas() : Call<String>

    @GET("get?token=$token&v4")
    fun getDataBeratTimbangan() : Call<String>

    //    Start of Jam
    @GET("get?token=$token&v9")
    fun getDataJam1() : Call<String>

    @GET("update?token=$token")
    fun setDataJam1(
        @Query("v9") v9: Int
    ) : Call<String>

    @GET("get?token=$token&v10")
    fun getDataJam2() : Call<String>

    @GET("update?token=$token")
    fun setDataJam2(
        @Query("v10") v10: Int
    ) : Call<String>

    @GET("get?token=$token&v11")
    fun getDataJam3() : Call<String>

    @GET("update?token=$token")
    fun setDataJam3(
        @Query("v11") v11: Int
    ) : Call<String>
    //    End of Jam

    //    Start of Menit
    @GET("get?token=$token&v13")
    fun getDataMenit1() : Call<String>

    @GET("update?token=$token")
    fun setDataMenit1(
        @Query("v13") v13: Int
    ) : Call<String>

    @GET("get?token=$token&v14")
    fun getDataMenit2() : Call<String>

    @GET("update?token=$token")
    fun setDataMenit2(
        @Query("v14") v14: Int
    ) : Call<String>

    @GET("get?token=$token&v15")
    fun getDataMenit3() : Call<String>

    @GET("update?token=$token")
    fun setDataMenit3(
        @Query("v15") v15: Int
    ) : Call<String>
    //    End of Menit

    //    Start of Berat
    @GET("get?token=$token&v17")
    fun getDataBerat1() : Call<String>

    @GET("update?token=$token")
    fun setDataBerat1(
        @Query("v17") v17: Int
    ) : Call<String>

    @GET("get?token=$token&v18")
    fun getDataBerat2() : Call<String>

    @GET("update?token=$token")
    fun setDataBerat2(
        @Query("v18") v18: Int
    ) : Call<String>

    @GET("get?token=$token&v19")
    fun getDataBerat3() : Call<String>

    @GET("update?token=$token")
    fun setDataBerat3(
        @Query("v19") v19: Int
    ) : Call<String>
    //    End of Berat

    //    Switch
    @GET("get?token=$token&v21")
    fun getDataSwitch() : Call<Int>

    @GET("update?token=$token")
    fun setDataSwitch(
        @Query("v21") v21: Int
    ) : Call<Int>
    //    End of Switch
}