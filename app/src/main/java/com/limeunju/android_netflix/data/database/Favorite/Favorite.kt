package com.limeunju.android_netflix.data.database.Favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val fid: Int
)