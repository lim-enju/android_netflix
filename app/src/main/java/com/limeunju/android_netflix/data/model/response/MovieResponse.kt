package com.limeunju.android_netflix.data.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

data class MovieResponse(
    @SerializedName("page")
    var page: Int,

    @SerializedName("results")
    var movies: List<Movie> = listOf()
)

@Entity(tableName = "favorite")
@Parcelize
data class Movie (

    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo("title")
    @SerializedName("title")
    var title: String? = null,

    @ColumnInfo("poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null,

    @ColumnInfo("release_date")
    @SerializedName("release_date")
    var releaseDate: String? = null,

    @ColumnInfo("popularity")
    @SerializedName("popularity")
    var popularity: String? = null
): Parcelable