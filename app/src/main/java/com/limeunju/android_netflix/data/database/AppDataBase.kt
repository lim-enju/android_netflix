package com.limeunju.android_netflix.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.limeunju.android_netflix.data.database.Favorite.FavoriteDao
import com.limeunju.android_netflix.data.database.Favorite.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}