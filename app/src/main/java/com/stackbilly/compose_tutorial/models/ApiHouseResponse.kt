package com.stackbilly.compose_tutorial.models

import com.google.gson.annotations.SerializedName

data class ApiHouseResponse(
    @SerializedName("error" ) var error: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("results"    ) var results: MutableList<Houses> = mutableListOf(),
    )
