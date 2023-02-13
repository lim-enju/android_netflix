package com.limeunju.android_netflix.data.database.Favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.limeunju.android_netflix.data.model.response.Movie

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val fid: Int,

    @ColumnInfo("title"      ) var title      : String? = null,
    @ColumnInfo("link"       ) var link       : String? = null,
    @ColumnInfo("image"      ) var image      : String? = null,
    @ColumnInfo("subtitle"   ) var subtitle   : String? = null,
    @ColumnInfo("pubDate"    ) var pubDate    : String? = null,
    @ColumnInfo("director"   ) var director   : String? = null,
    @ColumnInfo("actor"      ) var actor      : String? = null,
    @ColumnInfo("userRating" ) var userRating : String? = null
){
    constructor(
        title: String?,
        link: String?,
        image: String?,
        subtitle: String?,
        pubDate: String?,
        director: String?,
        actor: String?,
        userRating: String?,
    ) : this(0, title, link, image, subtitle, pubDate, director, actor, userRating)
}

fun Favorite.toMovie() =
    Movie(
        title = title,
        link = link,
        image = image,
        subtitle = subtitle,
        pubDate = pubDate,
        director = director,
        actor = actor,
        userRating = userRating
    )