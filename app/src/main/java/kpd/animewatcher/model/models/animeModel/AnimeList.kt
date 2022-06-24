package kpd.animewatcher.model.models.animeModel

data class AnimeList(
    val `data`: Data,
    val message: String,
    val status_code: Int,
    val version: String
)