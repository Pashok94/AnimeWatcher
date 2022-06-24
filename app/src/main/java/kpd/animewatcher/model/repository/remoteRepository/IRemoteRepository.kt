package kpd.animewatcher.model.repository.remoteRepository


interface IRemoteRepository {
    suspend fun loadAnime(page: Int): Result
}