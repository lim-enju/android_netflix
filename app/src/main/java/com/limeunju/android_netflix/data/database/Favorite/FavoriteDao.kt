package com.limeunju.android_netflix.data.database.Favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<Favorite>>

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}