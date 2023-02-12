package com.limeunju.android_netflix.data.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase) = appDatabase.favoriteDao()
}