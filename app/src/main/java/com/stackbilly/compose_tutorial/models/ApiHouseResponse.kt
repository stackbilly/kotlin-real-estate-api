package com.stackbilly.compose_tutorial.models

import com.google.gson.annotations.SerializedName

data class ApiHouseResponse(
    @SerializedName("Description" ) var Description : String? = null,
    @SerializedName("Image"       ) var Image       : String? = null,
    @SerializedName("Location"    ) var Location    : String? = null,
    @SerializedName("Name"        ) var Name        : String? = null,
    @SerializedName("Price"       ) var Price       : String? = null,
    @SerializedName("Url"         ) var Url         : String? = null,
    @SerializedName("_id"         ) var Id          : String? = null

)
