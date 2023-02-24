package com.limeunju.android_netflix.data.database.Favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.limeunju.android_netflix.data.model.response.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<Movie>>

    @Insert
    fun insert(favorite: Movie)

    @Query("DELETE FROM favorite WHERE title = :title")
    fun delete(title: String)
}