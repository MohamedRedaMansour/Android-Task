package com.mansour.taskassignment.models.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataResponse (

    @Expose
    @SerializedName("userId")
    val userId: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("completed")
    val completed: String,
    @Expose
    @SerializedName("message") // for failure
    val message: String,
)