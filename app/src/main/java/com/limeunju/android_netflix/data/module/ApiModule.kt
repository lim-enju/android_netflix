package com.limeunju.android_netflix.data.module

import com.limeunju.android_netflix.BuildConfig
import com.limeunju.android_netflix.data.database.Favorite.FavoriteDao
import com.limeunju.android_netflix.data.datasource.MovieDataSource
import com.limeunju.android_netflix.data.repository.MovieRepository
import com.limeunju.android_netflix.data.service.ApiService
import com.limeunju.android_netflix.data.usecase.MovieUseCase
import com.limeunju.android_netflix.data.util.ResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object ApiModule {

    @Provides
    fun provideBaseUrl() = "https://openapi.naver.com/v1/"

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResponseAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDataSource(
        appDatabase: AppDatabase,
        apiService: ApiService
    ): MovieDataSource = MovieDataSource(appDatabase, apiService)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService
        = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMovieRepository(movieDataSource:MovieDataSource) = MovieRepository(movieDataSource)

    @Singleton
    @Provides
    fun provideMovieUseCase(movieRepository: MovieRepository) = MovieUseCase(movieRepository)
}