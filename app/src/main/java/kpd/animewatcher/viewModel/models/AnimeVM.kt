package kpd.animewatcher.viewModel.models

import kpd.animewatcher.model.models.animeModel.Anime

data class AnimeVM(
    val id: Int,
    val title: String?,
    val posterUrl: String?,
    val description: String?,
    val genres: List<String?>,
    val trailerId: String?,
    val coverColor: String?,
    val coverImage: String?
) {
    companion object{
        fun fromModel(animeModel: Anime): AnimeVM{
            return AnimeVM(
                animeModel.id,
                animeModel.titles?.rj,
                animeModel.banner_image,
                animeModel.descriptions?.en,
                animeModel.genres,
                animeModel.trailer_url?.substringAfter("https://www.youtube.com/embed/"),
                animeModel.cover_color,
                animeModel.cover_image
            )
        }
    }
}