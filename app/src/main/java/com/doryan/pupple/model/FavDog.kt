package com.doryan.pupple.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_dogs")
data class FavDog(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "image_url")
    var imgUrl: String,

    @ColumnInfo(name = "created_time")
    var createdTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "name")
    var name: String = ""
)
