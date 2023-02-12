package com.limeunju.android_netflix.data.database.Favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val fid: Int,

    @ColumnInfo("name") val name: String
){
    constructor(name: String) : this(0, name)
}