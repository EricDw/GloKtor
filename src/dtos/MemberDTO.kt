package dtos

import com.google.gson.annotations.SerializedName

typealias Members = List<MemberDTO>

data class MemberDTO(
    val id: String,
    @SerializedName("username")
    val userName: String,
    val role: String = ""
)