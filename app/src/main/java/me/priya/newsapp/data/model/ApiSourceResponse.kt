package me.priya.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiSourceResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("sources")
    val sources: List<ApiSource>
)