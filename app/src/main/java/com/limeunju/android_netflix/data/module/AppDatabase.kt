package com.limeunju.android_netflix.data.module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.limeunju.android_netflix.data.database.Favorite.FavoriteDao
import com.limeunju.android_netflix.data.model.response.Movie

@Database(
    entities = [
        Movie::class
    ], version = 1
)
//companion object로 싱글톤 db 만들어서 바로 불러서 썼던 것과 다르게,
//hilt에서는 추상클래스와 추상메소드로 만들고 module에서 구현해 준다음
//실제 사용하는 곳에서는 inject 해서 사용한다.
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}