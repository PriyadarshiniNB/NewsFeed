package me.priya.newsapp.data.model

import com.google.gson.annotations.SerializedName
import me.priya.newsapp.data.local.entity.Source

data class ApiSource(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String = "",
)

fun ApiSource.toSourceEntity(): Source {
    return Source(id, name)
}
