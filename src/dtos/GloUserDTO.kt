package net.publicmethod.dtos

import com.google.gson.annotations.SerializedName

data class GloUserDTO(
    val id: String,
    @SerializedName("username")
    val userName: String
)