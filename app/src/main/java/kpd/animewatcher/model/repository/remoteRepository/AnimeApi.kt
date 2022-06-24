package kpd.animewatcher.model.repository.remoteRepository

import kpd.animewatcher.model.models.animeModel.AnimeList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {
    @Retry
    @GET("/v1/anime")
    suspend fun loadAnime(
        @Query("page") page: Int
    ): Response<AnimeList>

    @Retry
    @GET("/api/edge/anime")
    suspend fun loadAnimeById(): Response<AnimeList>
}