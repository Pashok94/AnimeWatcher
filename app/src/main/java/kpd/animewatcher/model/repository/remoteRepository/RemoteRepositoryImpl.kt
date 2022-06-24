package kpd.animewatcher.model.repository.remoteRepository


class RemoteRepositoryImpl(
    val animeApi: AnimeApi
) : IRemoteRepository {
    override suspend fun loadAnime(page: Int): Result {
        val response = animeApi.loadAnime(page)
        if (response.isSuccessful)
            response.body()?.apply {
                return Result.Success(this)
            }
        return Result.Error("failed load anime")
    }
}