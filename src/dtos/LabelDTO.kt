package dtos

import com.google.gson.annotations.SerializedName

typealias Labels = List<LabelDTO>

data class LabelDTO(
    val id: String = "",
    val name: String = "",
    val color: ColorDTO = ColorDTO(),
    @SerializedName("created_by")
    val createdBy: CreatedByDTO = CreatedByDTO(),
    @SerializedName("created_date")
    val createdDate: String = ""
)