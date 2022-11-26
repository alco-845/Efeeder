package com.alcorp.efeeder.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getById(username: String, password: String) : List<User>
}