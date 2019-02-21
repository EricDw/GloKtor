package dtos

import com.google.gson.annotations.SerializedName

typealias InvitedMembers = List<InvitedMemberDTO>

data class InvitedMemberDTO(
    val id: String,
    val role: String,
    @SerializedName("username")
    val userName: String
)