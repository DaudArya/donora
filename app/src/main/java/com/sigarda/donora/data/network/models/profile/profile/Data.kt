package com.sigarda.donora.data.network.models.profile.profile

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("nik")
    val NIK: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("address")
    val address: Any?,
    @SerializedName("age")
    val age: Any?,
    @SerializedName("blood")
    val blood: Any?,
    @SerializedName("blood_id")
    val blood_id: Any?,
    @SerializedName("code_donor")
    val code_donor: Any?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("donor_historis")
    val donor_historis: List<Any>?,
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_number")
    val phone_number: Any?,
    @SerializedName("point")
    val point: Any?,
    @SerializedName("profile_picture")
    val profile_picture: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("updated_at")
    val updated_at: String?,
    @SerializedName("user_id")
    val user_id: String?,
    @SerializedName("user_rewards")
    val user_rewards: List<Any>?,
    @SerializedName("village")
    val village: Any?,
    @SerializedName("village_id")
    val village_id: Any?


)