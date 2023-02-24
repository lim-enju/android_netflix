package com.limeunju.android_netflix.data.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

data class MovieResponse (
    @SerializedName("lastBuildDate") var lastBuildDate : String? = null,

    @SerializedName("total")
    var total: Int? = null,

    @SerializedName("start")
    var start: Int? = null,

    @SerializedName("display")
    var display: Int? = null,

    @SerializedName("items")
    var movies: ArrayList<Movie> = arrayListOf()
)

@Entity(tableName = "favorite")
@Parcelize
data class Movie (

    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true)
    var fid: Int,

    @ColumnInfo("title")
    @SerializedName("title")
    var title: String? = null,

    @ColumnInfo("link")
    @SerializedName("link")
    var link: String? = null,

    @ColumnInfo("image")
    @SerializedName("image")
    var image: String? = null,

    @ColumnInfo("subtitle")
    @SerializedName("subtitle")
    var subtitle: String? = null,

    @ColumnInfo("pubDate")
    @SerializedName("pubDate")
    var pubDate: String? = null,

    @ColumnInfo("director")
    @SerializedName("director")
    var director: String? = null,

    @ColumnInfo("actor")
    @SerializedName("actor")
    var actor: String? = null,

    @ColumnInfo("userRating")
    @SerializedName("userRating")
    var userRating: String? = null
): Parcelable