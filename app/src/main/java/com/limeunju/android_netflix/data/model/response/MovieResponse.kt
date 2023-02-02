package com.limeunju.android_netflix.data.model.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieResponse (
    @SerializedName("lastBuildDate" ) var lastBuildDate : String?          = null,
    @SerializedName("total"         ) var total         : Int?             = null,
    @SerializedName("start"         ) var start         : Int?             = null,
    @SerializedName("display"       ) var display       : Int?             = null,
    @SerializedName("items"         ) var items         : ArrayList<Items> = arrayListOf()
)

data class Items (
    @SerializedName("title"      ) var title      : String? = null,
    @SerializedName("link"       ) var link       : String? = null,
    @SerializedName("image"      ) var image      : String? = null,
    @SerializedName("subtitle"   ) var subtitle   : String? = null,
    @SerializedName("pubDate"    ) var pubDate    : String? = null,
    @SerializedName("director"   ) var director   : String? = null,
    @SerializedName("actor"      ) var actor      : String? = null,
    @SerializedName("userRating" ) var userRating : String? = null
)