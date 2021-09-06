package com.doryan.pupple.network.response

import com.squareup.moshi.Json

data class RandomNameResponse(
    @Json(name = "results")
    private val _results: List<Result>,
) {
    data class Result(
        val name: RandomNameProperty
    )

    val properties: List<RandomNameProperty> = _results.map { it.name }
}

data class RandomNameProperty(
    @Json(name = "first")
    val firstName: String = "",
    @Json(name = "last")
    val LastName: String = ""

)