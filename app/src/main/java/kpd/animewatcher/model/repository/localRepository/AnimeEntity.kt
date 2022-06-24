package kpd.animewatcher.model.repository.localRepository

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kpd.animewatcher.model.models.animeModel.Descriptions
import kpd.animewatcher.model.models.animeModel.Titles
import kpd.animewatcher.model.models.animeModel.saga.Saga

@Entity(tableName = "AnimeEntity")
data class AnimeEntity(
    @PrimaryKey val anilist_id: Int,
    val banner_image: String?,
    val cover_color: String?,
    val cover_image: String?,
    @Embedded
    val descriptions: Descriptions?,
    val end_date: String?,
    val episode_duration: Int?,
    val episodes_count: Int?,
    val format: Int?,
    val genres: List<String?>,
    val has_cover_image: Boolean?,
    val id: Int?,
    val mal_id: Int?,
    val nsfw: Boolean?,
    val prequel: Int?,
    val recommendations: List<Int?>,
    val sagas: List<Saga>?,
    val score: Int?,
    val season_period: Int?,
    val season_year: Int?,
    val sequel: Int?,
    val start_date: String?,
    val status: Int?,
    @Embedded
    val titles: Titles?,
    val tmdb_id: Int?,
    val trailer_url: String?,
    val weekly_airing_day: Int?
)