package dtos

import com.google.gson.annotations.SerializedName

data class ColorDTO(
    @SerializedName("r")
    val red: Int = 0,
    @SerializedName("g")
    val green: Int = 0,
    @SerializedName("b")
    val blue: Int = 0,
    @SerializedName("a")
    val alpha: Int = 0
)