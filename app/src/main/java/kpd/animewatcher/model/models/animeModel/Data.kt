package kpd.animewatcher.model.models.animeModel

import com.google.gson.annotations.SerializedName

data class Data(
    val count: Int,
    val current_page: Int,
    @SerializedName("documents")
    val anime: List<Anime>,
    val last_page: Int
)