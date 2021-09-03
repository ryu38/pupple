package com.doryan.pupple.network.response

import com.squareup.moshi.Json

class DogResponse (
    @Json(name = "message")
    private val _dogProperties: List<String>,

    val status: String
) {
    val properties = _dogProperties.map {
        DogProperty(it)
    }
}

data class DogProperty(
    val imageUrl: String
)
