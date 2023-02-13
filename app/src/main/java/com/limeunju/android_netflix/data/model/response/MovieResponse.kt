package com.limeunju.android_netflix.data.model.response

import com.google.gson.annotations.SerializedName
import com.limeunju.android_netflix.data.database.Favorite.Favorite
import java.util.*

data class MovieResponse (
    @SerializedName("lastBuildDate" ) var lastBuildDate : String?          = null,
    @SerializedName("total"         ) var total         : Int?             = null,
    @SerializedName("start"         ) var start         : Int?             = null,
    @SerializedName("display"       ) var display       : Int?             = null,
    @SerializedName("items"         ) var movies         : ArrayList<Movie> = arrayListOf()
)

data class Movie (
    @SerializedName("title"      ) var title      : String? = null,
    @SerializedName("link"       ) var link       : String? = null,
    @SerializedName("image"      ) var image      : String? = null,
    @SerializedName("subtitle"   ) var subtitle   : String? = null,
    @SerializedName("pubDate"    ) var pubDate    : String? = null,
    @SerializedName("director"   ) var director   : String? = null,
    @SerializedName("actor"      ) var actor      : String? = null,
    @SerializedName("userRating" ) var userRating : String? = null
)

fun Movie.toFavorite() =
    Favorite(
        title = title,
        link = link,
        image = image,
        subtitle = subtitle,
        pubDate = pubDate,
        director = director,
        actor = actor,
        userRating = userRating
    )