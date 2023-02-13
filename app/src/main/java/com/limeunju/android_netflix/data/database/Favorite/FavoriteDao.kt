package com.limeunju.android_netflix.data.database.Favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<Favorite>>

    @Insert
    fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE title = :title")
    fun delete(title: String)
}